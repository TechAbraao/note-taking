package dev.himanshu.noteTaking.dto.request

import dev.himanshu.noteTaking.utils.Priority
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class NoteRequest(
    val id: String = UUID.randomUUID().toString(),

    @field:NotBlank(message = "The field cannot be empty.")
    val title: String,

    @field:NotBlank(message = "The field cannot be empty.")
    val description: String,

    @field:NotNull(message = "Priority cannot be null.")
    val priority: Priority,

    @field:Size(min = 1, message = "Tags list cannot be empty.")
    val tags: List<String>,
)