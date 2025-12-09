package dev.himanshu.noteTaking.controllers

import dev.himanshu.noteTaking.dto.NoteDTO
import dev.himanshu.noteTaking.dto.request.NoteRequest
import dev.himanshu.noteTaking.entities.NoteEntity
import dev.himanshu.noteTaking.services.NoteServices
import dev.himanshu.noteTaking.utils.ApiResponse
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
class NoteController(
    private val noteServices: NoteServices
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllNotes(): ResponseEntity<ApiResponse<List<NoteDTO>>> {
        val allNotes: List<NoteDTO> = noteServices.all()

        if (allNotes.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                    ApiResponse(
                        statusCode = HttpStatus.NO_CONTENT.value(),
                        message = "Lista de notas não encontradas.",
                    )
                )
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse(
                    statusCode = HttpStatus.OK.value(),
                    message = "Todas as notas encontradas com sucesso.",
                    data = allNotes
                )
            )
    }

    @GetMapping("{id}")
    fun getNoteById(@PathVariable("id") id: UUID): ResponseEntity<ApiResponse<NoteDTO>> {
        val note: NoteDTO = noteServices.findById(id = id)
            ?: return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse(
                        statusCode = HttpStatus.NOT_FOUND.value(),
                        message = "Nota não encontrada.",
                    )
                )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse(
                    statusCode = HttpStatus.OK.value(),
                    message = "Nota encontrada com sucesso.",
                    data = note
                )
            )
    }

    @PostMapping
    fun postNote(@Valid @RequestBody request: NoteRequest): ResponseEntity<ApiResponse<NoteDTO>> {
        val createdNote: NoteDTO = noteServices.create(request) ?: return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse(
                    statusCode = HttpStatus.BAD_REQUEST.value(),
                    message = "Tìtulo duplicado, insira um título válido."
                )
            )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    statusCode = HttpStatus.CREATED.value(),
                    message = "Nota criada com sucesso.",
                    data = createdNote
                )
            )
    }

    @DeleteMapping("{id}")
    fun deleteNote(@PathVariable id: UUID): ResponseEntity<ApiResponse<Unit>> {
        val deleted = noteServices.deleteById(id)

        return if (deleted)
            ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build()
        else
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(
                    statusCode = HttpStatus.NOT_FOUND.value(),
                    message = "Nota não encontrada."
                ))
    }

    @PutMapping("{id}")
    fun updateNote(
        @PathVariable id: UUID,
        @RequestBody request: NoteRequest
    ): ResponseEntity<ApiResponse<NoteDTO>> {

        val updated = noteServices.update(id, request)
            ?: return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(
                    statusCode = HttpStatus.NOT_FOUND.value(),
                    message = "Nenhuma nota alterada."
                ))

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse(
                statusCode = HttpStatus.OK.value(),
                message = "Nota alterada com sucesso.",
                data = updated
            ))
    }
}