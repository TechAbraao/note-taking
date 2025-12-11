package dev.himanshu.noteTaking.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Pattern
import java.util.UUID

data class TagRequest(
    val id: String = UUID.randomUUID().toString(),

    @field:NotBlank(message = "The field cannot be empty.")
    @field:Size(min = 2, max = 15, message = "The maximum length of that field is required.")
    @field:Pattern(
        regexp = "^[A-Za-z]+$",
        message = "Characters containing accents and spaces are invalid."
    )
    val name: String
)