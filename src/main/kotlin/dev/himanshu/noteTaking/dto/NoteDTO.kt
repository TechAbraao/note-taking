package dev.himanshu.noteTaking.dto
import java.util.UUID

data class NoteDTO(
    val id: UUID?,
    val title: String,
    val description: String,
)
