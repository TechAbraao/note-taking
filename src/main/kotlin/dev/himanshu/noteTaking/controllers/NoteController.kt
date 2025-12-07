package dev.himanshu.noteTaking.controllers

import dev.himanshu.noteTaking.dto.NoteRequest
import dev.himanshu.noteTaking.services.NoteServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class NoteController (
private val noteServices: NoteServices
) {
    @GetMapping("/api/notes")
    fun getNotes() : ResponseEntity<List<NoteRequest>> {

        val notes: List<NoteRequest> = noteServices.getNotes()
        if (notes.isEmpty()) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(notes)
    }

    @GetMapping("/api/notes/{id}")
    fun getNoteById(@PathVariable("id") id: UUID) : ResponseEntity<NoteRequest> {
        val note: NoteRequest = noteServices.getNoteById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(note)
    };

    @PostMapping("/api/notes")
    fun postNotes(@Valid @RequestBody note: NoteRequest): ResponseEntity<NoteRequest> {
        noteServices.postNote(note)
        return ResponseEntity
            .status(201)
            .body(note);
    }
}