package dev.himanshu.noteTaking.dto.request

import dev.himanshu.noteTaking.dto.NoteDTO
import dev.himanshu.noteTaking.entities.NoteEntity
import dev.himanshu.noteTaking.utils.Priority
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class NoteRequest(
    val id: String = UUID.randomUUID().toString(),

    @field:NotBlank(message = "The field cannot be empty.")
    val title: String,

    @field:NotBlank(message = "The field cannot be empty.")
    val description: String,

    @field:NotNull(message = "Priority cannot be null.")
    val priority: Priority,

    @field:NotNull(message = "Field list cannot be empty.")
    val tags: List<String>,

    ) {
    fun toEntity(): NoteEntity = NoteEntity(
        title = title,
        description = description,
        priority = priority
    )

    fun toDTO(noteEntity: NoteEntity): NoteDTO {
        return NoteDTO(
            id = noteEntity.id,
            title = noteEntity.title,
            description = noteEntity.description,
            priority = noteEntity.priority
        )
    }
}