package dev.himanshu.noteTaking.dto.request

import dev.himanshu.noteTaking.entities.TagEntity
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class TagRequest(

    val id: String = UUID.randomUUID().toString(),

    @field:NotBlank(message = "The field cannot be empty.")
    val name: String

) {
    fun toEntity(): TagEntity {
        return TagEntity(
            name = name
        )
    }
}