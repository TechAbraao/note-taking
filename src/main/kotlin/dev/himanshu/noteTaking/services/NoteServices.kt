package dev.himanshu.noteTaking.services

import dev.himanshu.noteTaking.models.Note
import dev.himanshu.noteTaking.repository.NoteRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NoteServices (
    val noteRepository: NoteRepository
) {
    fun getNotes(): List<Note> = noteRepository.getNotes();
    fun postNote(note: Note) = noteRepository.addNote(note);
    fun getNoteById(id: UUID) = noteRepository.getNoteById(id)
}