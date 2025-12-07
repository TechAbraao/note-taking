package dev.himanshu.noteTaking.services

import dev.himanshu.noteTaking.dto.NoteRequest
import dev.himanshu.noteTaking.repository.NoteRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NoteServices (
    val noteRepository: NoteRepository
) {
    fun getNotes(): List<NoteRequest> = noteRepository.getNotes();
    fun postNote(note: NoteRequest) = noteRepository.addNote(note);
    fun getNoteById(id: UUID) = noteRepository.getNoteById(id)
}