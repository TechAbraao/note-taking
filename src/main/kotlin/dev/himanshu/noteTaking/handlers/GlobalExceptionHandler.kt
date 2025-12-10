package dev.himanshu.noteTaking.handlers

import dev.himanshu.noteTaking.dto.response.ErrorResponse
import dev.himanshu.noteTaking.exceptions.NoteTitleAlreadyExistsException
import dev.himanshu.noteTaking.exceptions.TagAlreadyExistsException
import dev.himanshu.noteTaking.exceptions.TagNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Map<String, Any>> {

        val origin = ex.stackTrace.firstOrNull { it.className.startsWith("dev.himanshu") }?.let {
            "${it.className}.${it.methodName}:${it.lineNumber}"
        } ?: "Unknown origin"

        val body = mapOf(
            "statusCode" to HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "exceptionType" to ex.javaClass.name,
            "message" to ex.message,
            "origin" to origin
        )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(body) as ResponseEntity<Map<String, Any>>
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {

        val errors = ex.bindingResult.fieldErrors.associate { error ->
            error.field to (error.defaultMessage ?: "Invalid field")
        }

        val body = ErrorResponse(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            message = errors
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body) as ResponseEntity<Map<String, Any>>
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleValidationHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            message = (ex.localizedMessage ?: "Malformed JSON request.")
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TagAlreadyExistsException::class)
    fun handleTagAlreadyExistsException(ex: TagAlreadyExistsException): ResponseEntity<ErrorResponse?> {

        val body = ErrorResponse(
            statusCode = HttpStatus.CONFLICT.value(),
            message = (ex.localizedMessage ?: "Tag already exists.")
        )

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(body)
    }

    @ExceptionHandler(NoteTitleAlreadyExistsException::class)
    fun handleNoteTitleAlreadyExistsException(ex: NoteTitleAlreadyExistsException): ResponseEntity<ErrorResponse?> {

        val body = ErrorResponse(
            statusCode = HttpStatus.CONFLICT.value(),
            message = (ex.localizedMessage ?: "Note with title already exists.")
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body)
    }

    @ExceptionHandler(TagNotFoundException::class)
    fun handleTagNotFoundException(ex: TagNotFoundException): ResponseEntity<ErrorResponse?> {

        val body = ErrorResponse(
            statusCode = HttpStatus.NOT_FOUND.value(),
            message = (ex.localizedMessage ?: "Tag not found.")
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body)
    }
}