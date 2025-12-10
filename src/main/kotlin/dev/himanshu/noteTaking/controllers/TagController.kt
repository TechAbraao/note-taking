package dev.himanshu.noteTaking.controllers

import dev.himanshu.noteTaking.dto.TagDTO
import dev.himanshu.noteTaking.dto.request.TagRequest
import dev.himanshu.noteTaking.services.TagServices
import dev.himanshu.noteTaking.utils.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/tags")
class TagController(
    private val tagServices: TagServices
) {
    @PostMapping
    fun postTag(@Valid @RequestBody request: TagRequest): ResponseEntity<ApiResponse<TagDTO>> {
        var saved = tagServices.create(request = request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    message = "Tag criada com sucesso.",
                    statusCode = HttpStatus.CREATED.value()
                )
            )
    }

    @DeleteMapping("{name}")
    fun deleteByName(@PathVariable name: String): ResponseEntity<ApiResponse<Boolean>> {

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(
                ApiResponse(
                    message = "Tag cannot be deleted",
                    statusCode = HttpStatus.NO_CONTENT.value()
                )
            )
    }

    @DeleteMapping("{id}")
    fun deleteById(@PathVariable id: UUID): ResponseEntity<ApiResponse<Boolean>> {
        TODO(reason = "Not implemented yet.")
    }
}