package dev.himanshu.noteTaking.repository

import dev.himanshu.noteTaking.dto.NoteRequest
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class NoteRepository {
    val mockNotes: MutableList<NoteRequest> = mutableListOf<NoteRequest>()
    fun getNotes() = mockNotes
    fun addNote(note: NoteRequest) = mockNotes.add(note);
    fun getNoteById(id: UUID) = mockNotes.find { it.id == id.toString() }
}