package dev.himanshu.noteTaking.handlers

import dev.himanshu.noteTaking.exceptions.NoteTitleAlreadyExistsException
import dev.himanshu.noteTaking.exceptions.TagAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {

        val errors = ex.bindingResult.fieldErrors.associate { error ->
            error.field to (error.defaultMessage ?: "Invalid field")
        }

        val body = mapOf(
            "statusCode" to 400,
            "errors" to errors
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleValidationHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, Any>> {
        val body = mapOf(
            "statusCode" to 400,
            "error" to (ex.localizedMessage ?: "Malformed JSON request.")
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TagAlreadyExistsException::class)
    fun handleTagAlreadyExistsException(ex: TagAlreadyExistsException): ResponseEntity<Map<String, Any>> {
        val body = mapOf(
            "statusCode" to HttpStatus.CONFLICT.value(),
            "error" to (ex.localizedMessage ?: "Tag already exists.")
        )
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(body)
    }

    @ExceptionHandler(NoteTitleAlreadyExistsException::class)
    fun handleNoteTitleAlreadyExistsException(ex: NoteTitleAlreadyExistsException): ResponseEntity<Map<String, Any>> {
        val body = mapOf(
            "statusCode" to HttpStatus.CONFLICT.value(),
            "error" to (ex.localizedMessage ?: "Note with title already exists.")
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body)
    }
}