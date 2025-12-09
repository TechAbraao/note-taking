package dev.himanshu.noteTaking.services
import dev.himanshu.noteTaking.dto.NoteDTO
import dev.himanshu.noteTaking.dto.request.NoteRequest
import dev.himanshu.noteTaking.entities.NoteEntity
import dev.himanshu.noteTaking.entities.TagEntity
import dev.himanshu.noteTaking.exceptions.NoteTitleAlreadyExistsException
import dev.himanshu.noteTaking.exceptions.TagAlreadyExistsException
import dev.himanshu.noteTaking.repository.NoteRepository
import dev.himanshu.noteTaking.repository.TagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory
import java.util.UUID

@Service
class NoteServices(
    val noteRepository: NoteRepository,
    val tagRepository: TagRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun all(): List<NoteDTO> {
        logger.info("Retornando todas as notas.")
        return noteRepository.findAll().map { note ->
            NoteDTO(
                id = note.id,
                title = note.title,
                description = note.description,
                priority = note.priority
            )
        }
    }

    fun create(request: NoteRequest): NoteDTO? {

        val tags = request.tags.map {
            tagName -> tagRepository.findByName(tagName) ?: TagEntity(name = tagName)
        }.toMutableList()
        logger.info("Tags encontradas: $tags.")


        if (noteRepository.existsByTitle(request.title)) {
            throw NoteTitleAlreadyExistsException(
                message = "Note with title '${request.title}' already exists."
            )
        }

        val noteSaved: NoteEntity = noteRepository.save(request.toEntity())

        return NoteDTO(
            id = noteSaved.id,
            title = noteSaved.title,
            description = noteSaved.description,
            priority = noteSaved.priority
        )
    }

    fun deleteById(id: UUID): Boolean {
        return if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id)
            logger.info("Nota $id removida com sucesso.")
            true
        } else {
            logger.warn("Tentativa de deletar nota inexistente: id=$id")
            false
        }
    }

    fun findById(id: UUID) : NoteDTO? {
        logger.info("Retornando nota com ID: '$id'.")
        val note = noteRepository.findByIdOrNull(id) ?: return null
        val noteDTO = NoteDTO(
            id = note.id,
            title = note.title,
            description = note.description,
            priority = note.priority
        )

        logger.info("Nota encontrada: '$noteDTO'.")
        return noteDTO
    }

    fun update(id: UUID, request: NoteRequest): NoteDTO? {
        val entity = noteRepository.findByIdOrNull(id) ?: return null

        val updatedEntity = entity.copy(
            title = request.title,
            description = request.description,
            priority = request.priority
        )

        val saved = noteRepository.save(updatedEntity)

        return NoteDTO(
            id = saved.id!!,
            title = saved.title,
            description = saved.description,
            priority = saved.priority
        )
    }

}