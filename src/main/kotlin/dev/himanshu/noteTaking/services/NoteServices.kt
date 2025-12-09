package dev.himanshu.noteTaking.services
import dev.himanshu.noteTaking.dto.NoteDTO
import dev.himanshu.noteTaking.dto.NoteRequest
import dev.himanshu.noteTaking.entities.NoteEntity
import dev.himanshu.noteTaking.repository.NoteRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory
import java.util.UUID

@Service
class NoteServices(val noteRepository: NoteRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun all(): List<NoteDTO> {
        logger.info("Retornando todas as notas.")
        return noteRepository.findAll().map { note ->
            NoteDTO(
                id = note.id,
                title = note.title,
                description = note.description
            )
        }
    }

    fun create(dto: NoteRequest): NoteDTO? {
        if (noteRepository.findByTitle(dto.title) != null) {
            return null
        }

        val noteSaved: NoteEntity = noteRepository.save(dto.toEntity())

        return NoteDTO(
            id = noteSaved.id,
            title = noteSaved.title,
            description = noteSaved.description,
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
            description = note.description
        )

        logger.info("Nota encontrada: '$noteDTO'.")
        return noteDTO
    }

    fun update(id: UUID, request: NoteRequest): NoteDTO? {
        val entity = noteRepository.findByIdOrNull(id) ?: return null

        val updatedEntity = entity.copy(
            title = request.title,
            description = request.description
        )

        val saved = noteRepository.save(updatedEntity)

        return NoteDTO(
            id = saved.id!!,
            title = saved.title,
            description = saved.description
        )
    }

}