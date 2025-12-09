package dev.himanshu.noteTaking.controllers

import dev.himanshu.noteTaking.dto.TagDTO
import dev.himanshu.noteTaking.dto.request.TagRequest
import dev.himanshu.noteTaking.services.TagServices
import dev.himanshu.noteTaking.utils.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}