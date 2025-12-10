package dev.himanshu.noteTaking.mappers

import dev.himanshu.noteTaking.dto.TagDTO
import dev.himanshu.noteTaking.dto.request.TagRequest
import dev.himanshu.noteTaking.entities.TagEntity

object TagMapper {
    fun toEntity(request: TagRequest): TagEntity {
        return TagEntity(
            name = request.name,
        )
    }

    fun toDTO(entity: TagEntity): TagDTO {
        return TagDTO(
            id = entity.id,
            name = entity.name
        )
    }
}