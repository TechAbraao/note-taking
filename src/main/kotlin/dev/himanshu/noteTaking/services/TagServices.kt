package dev.himanshu.noteTaking.services

import dev.himanshu.noteTaking.dto.TagDTO
import dev.himanshu.noteTaking.dto.request.TagRequest
import dev.himanshu.noteTaking.exceptions.TagAlreadyExistsException
import dev.himanshu.noteTaking.mappers.TagMapper
import dev.himanshu.noteTaking.repository.TagRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class TagServices (
    private val tagRepository: TagRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun create(request: TagRequest): TagDTO? {

        if (tagRepository.existsByName(request.name)) {
            throw TagAlreadyExistsException(
                message = "Tag with name '${request.name}' already exists."
            )
        }

        val entity = TagMapper.toEntity(request)
        val savedTag = tagRepository.save(entity)

        return TagMapper.toDTO(savedTag )
    }

    fun deleteByName(name: String) {

    }
}