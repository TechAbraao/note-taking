package dev.himanshu.noteTaking.repository

import dev.himanshu.noteTaking.models.Note
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class NoteRepository {
    val mockNotes: MutableList<Note> = mutableListOf<Note>()

    fun getNotes() = mockNotes
    fun addNote(note: Note) = mockNotes.add(note);
    fun getNoteById(id: UUID) = mockNotes.find { it.id == id.toString() }
}