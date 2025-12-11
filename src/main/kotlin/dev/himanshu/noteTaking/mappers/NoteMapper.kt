package dev.himanshu.noteTaking.mappers

import dev.himanshu.noteTaking.dto.NoteDTO
import dev.himanshu.noteTaking.dto.request.NoteRequest
import dev.himanshu.noteTaking.entities.NoteEntity

object NoteMapper {
    fun toEntity(request: NoteRequest) =
        NoteEntity(
            title = request.title,
            description = request.description,
            priority = request.priority
        )
    fun toDTO(entity: NoteEntity) =
        NoteDTO(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            priority = entity.priority,
            tags = entity.tags
        )
}
