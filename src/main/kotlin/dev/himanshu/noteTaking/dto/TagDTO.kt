package dev.himanshu.noteTaking.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.util.UUID

data class TagDTO(
    val id: UUID?,

    @field:Pattern(
        regexp = "^[A-Za-z]+$",
        message = "Characters containing accents and spaces are invalid."
    )
    @field:NotBlank(message = "The field cannot be empty.")
    @field:Size(min = 2, max = 15, message = "The maximum length of that field is required.")
    val name: String,
)
