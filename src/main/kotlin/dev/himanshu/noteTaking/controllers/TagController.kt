package dev.himanshu.noteTaking.controllers

import dev.himanshu.noteTaking.dto.TagDTO
import dev.himanshu.noteTaking.dto.request.TagRequest
import dev.himanshu.noteTaking.services.TagServices
import dev.himanshu.noteTaking.utils.ApiResponse
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    @GetMapping
    fun getAll(): ResponseEntity<ApiResponse<List<TagDTO>>> {
        val listAllTags = tagServices.allTags()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse(
                message = "All tags were successfully returned.",
                statusCode = HttpStatus.OK.value(),
                data = listAllTags
            ))
    }

    @PostMapping
    fun postTag(@Valid @RequestBody request: TagRequest): ResponseEntity<ApiResponse<TagDTO>> {
        val savedTag = tagServices.create(request = request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    message = "Tag created successfully.",
                    statusCode = HttpStatus.CREATED.value(),
                    data = savedTag
                )
            )
    }

    @DeleteMapping("{name}")
    fun deleteByName(@PathVariable name: String): ResponseEntity<ApiResponse<Unit>> {
        val tagDeletedByName = tagServices.deleteByName(name)

        if (!tagDeletedByName) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    message = "Error deleting tag.",
                    statusCode = HttpStatus.BAD_REQUEST.value()
                )
            )
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
            ApiResponse(
                message = "Tag cannot be deleted.",
                statusCode = HttpStatus.NO_CONTENT.value()
            )
        )
    }
}