package dev.himanshu.noteTaking.dto
import dev.himanshu.noteTaking.entities.TagEntity
import dev.himanshu.noteTaking.utils.Priority
import java.util.UUID

data class NoteDTO(
    val id: UUID?,
    val title: String,
    val description: String,
    val priority: Priority,
    val tags: MutableList<TagEntity>
)
