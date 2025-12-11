package dev.himanshu.noteTaking.services

import dev.himanshu.noteTaking.dto.TagDTO
import dev.himanshu.noteTaking.dto.request.TagRequest
import dev.himanshu.noteTaking.exceptions.TagAlreadyExistsException
import dev.himanshu.noteTaking.exceptions.TagNotFoundException
import dev.himanshu.noteTaking.mappers.TagMapper
import dev.himanshu.noteTaking.repository.NoteRepository
import dev.himanshu.noteTaking.repository.TagRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TagServices (
    private val tagRepository: TagRepository,
    private val noteRepository: NoteRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun create(request: TagRequest): TagDTO? {
        val formattedName = request.name.replaceFirstChar { it.uppercase() }

        if (tagRepository.existsByName(formattedName)) {
            throw TagAlreadyExistsException(
                message = "Tag with name '${formattedName}' already exists."
            )
        }
        val requestToSaved = request.copy(
            name = formattedName
        )
        
        val entity = TagMapper.toEntity(requestToSaved)
        val savedTag = tagRepository.save(entity)

        return TagMapper.toDTO(savedTag)
    }

    @Transactional
    fun deleteByName(name: String): Boolean {
        val formattedName: String = name.replaceFirstChar { it.uppercase() }

        if (!tagRepository.existsByName(formattedName)) {
            throw TagNotFoundException("Tag with name '$name' does not exist.")
        }

        noteRepository.findAll().forEach {
            note -> note.tags.removeIf { it.name == formattedName }
        }
        tagRepository.deleteByName(formattedName)

        return true
    }

    fun allTags(): List<TagDTO> {
        val allTags = tagRepository.findAll()
        return allTags.map { TagMapper.toDTO(it) }
    }

    @Transactional
    fun changeByName(name: String) {
        var formattedName = name.replaceFirstChar { it.uppercase() }
    }
}