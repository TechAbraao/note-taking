package dev.himanshu.noteTaking.dto
import dev.himanshu.noteTaking.entities.NoteEntity
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class NoteRequest(
    val id: String = UUID.randomUUID().toString(),

    @field:NotBlank(message = "The field cannot be empty.")
    val title: String,

    @field:NotBlank(message = "The field cannot be empty.")
    val description: String,
) {
    fun toEntity(): NoteEntity = NoteEntity(
        title = title,
        description = description
    )

    fun toDTO(noteEntity: NoteEntity): NoteDTO {
        return NoteDTO(
            id = noteEntity.id,
            title = noteEntity.title,
            description = noteEntity.description,
        )
    }
}