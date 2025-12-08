package dev.himanshu.noteTaking.controllers

import dev.himanshu.noteTaking.dto.NoteDTO
import dev.himanshu.noteTaking.dto.NoteRequest
import dev.himanshu.noteTaking.entities.NoteEntity
import dev.himanshu.noteTaking.services.NoteServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PutMapping
import java.util.UUID

@RestController
@RequestMapping("/api/notes")
class NoteController (
    private val noteServices: NoteServices
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllNotes() : ResponseEntity<List<NoteDTO>> {
        val allNotes: List<NoteDTO> = noteServices.getAllNotes()

        if (allNotes.isEmpty()) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }

        return ResponseEntity
            .ok(allNotes)
    }

    @GetMapping("{id}")
    fun getNoteById(@PathVariable("id") id: UUID) : ResponseEntity<NoteDTO> {
        val note: NoteDTO = noteServices.getNoteById(id = id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(note)
    }

    @PostMapping
    fun postNote(@Valid @RequestBody request: NoteRequest): ResponseEntity<NoteRequest> {
        val createdNote: NoteDTO = noteServices.postNote(request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(request)
    }

    @DeleteMapping("{id}")
    fun deleteNote(@PathVariable id: UUID): ResponseEntity<Void> {
        val deleted = noteServices.deleteNote(id)

        return if (deleted)
            ResponseEntity.noContent().build()
        else
            ResponseEntity.notFound().build()
    }

    @PutMapping("{id}")
    fun updateNote(
        @PathVariable id: UUID,
        @RequestBody request: NoteRequest
    ): ResponseEntity<NoteDTO> {

        val updated = noteServices.updateNote(id, request)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(updated)
    }
}