package dev.himanshu.noteTaking.dto
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class NoteRequest(
    val id: String = UUID.randomUUID().toString(),

    @field:NotBlank(message = "The field cannot be empty.")
    val title: String,

    @field:NotBlank(message = "The field cannot be empty.")
    val description: String,
)