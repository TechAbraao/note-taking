package dev.himanshu.noteTaking.services
import dev.himanshu.noteTaking.dto.NoteDTO
import dev.himanshu.noteTaking.dto.TagDTO
import dev.himanshu.noteTaking.dto.request.NoteRequest
import dev.himanshu.noteTaking.entities.NoteEntity
import dev.himanshu.noteTaking.entities.TagEntity
import dev.himanshu.noteTaking.exceptions.NoteTitleAlreadyExistsException
import dev.himanshu.noteTaking.mappers.NoteMapper
import dev.himanshu.noteTaking.repository.NoteRepository
import dev.himanshu.noteTaking.repository.TagRepository
import dev.himanshu.noteTaking.utils.Priority
import jakarta.transaction.Transactional
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

    @Transactional
    fun all(): List<NoteDTO> {
        return noteRepository.findAll().map { note -> NoteMapper.toDTO(note) }
    }

    @Transactional
    fun create(request: NoteRequest): NoteDTO? {

        // Salvando as tags existentes em uma estrutura 'toMutableList()'. //
        val existingTags = request
            .tags
            .mapNotNull { tagName -> tagRepository.findByName(tagName) }.toMutableList()
        logger.info("All existing tags persisted in the database: '$existingTags'.")

        // Salvando as tags inexistentes em uma estrutura 'toMutableList()'. //
        val nonExistentTags = request
            .tags
            .filter { tagName -> tagRepository.findByName(tagName) == null }
        logger.info("All tags not persisted in the database: '$nonExistentTags'.")

        // Persistindo no banco de dados as tags inexistentes. //
        val savedTags = nonExistentTags.map { tagName ->
            val formattedName = tagName.replaceFirstChar { it.uppercase() }
            tagRepository.save(TagEntity(name = formattedName))
        }

        // Unindo as tags inexistentes que persistiram com as tags existentes. //
        val allTags = existingTags + savedTags
        val tagDtosList: List<TagDTO> = allTags.map {
            TagDTO(id = it.id, name = it.name)
        }
        logger.info("All tags: {}", tagDtosList)

        if (noteRepository.existsByTitle(request.title)) {
            throw NoteTitleAlreadyExistsException(
                message = "Note with title '${request.title}' already exists."
            )
        }

        // Criar a nota sem salvar ainda. //
        val noteEntity = NoteMapper.toEntity(request)

        // Associar as tags. //
        noteEntity.tags.addAll(allTags)

        // Agora salva no BD já com as relações. //
        val noteSaved = noteRepository.save(noteEntity)

        return NoteMapper.toDTO(noteSaved)
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

    @Transactional
    fun findById(id: UUID): NoteDTO? {
        val note = noteRepository.findByIdOrNull(id) ?: return null
        val noteDTO = NoteMapper.toDTO(note)
        logger.info("Nota encontrada: '$noteDTO'.")
        return noteDTO
    }

    @Transactional
    fun update(id: UUID, request: NoteRequest): NoteDTO? {
        val entity = noteRepository.findByIdOrNull(id)
            ?: return null

        val existingTags = request.tags
            .mapNotNull { tagName -> tagRepository.findByName(tagName) }
            .toMutableSet()

        val newTags = request.tags
            .filter { tagRepository.findByName(it) == null }
            .map { tagName ->
                val formatted = tagName.replaceFirstChar { it.uppercase() }
                tagRepository.save(TagEntity(name = formatted))
            }

        val allTags = existingTags + newTags

        val updatedEntity = entity.copy(
            title = request.title,
            description = request.description,
            priority = request.priority,
        )

        updatedEntity.tags.clear()
        updatedEntity.tags.addAll(allTags)
        val saved = noteRepository.save(updatedEntity)

        return NoteDTO(
            id = saved.id!!,
            title = saved.title,
            description = saved.description,
            priority = saved.priority,
            tags = saved.tags
        )
    }
}
