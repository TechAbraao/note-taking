package dev.himanshu.noteTaking.utils
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val message: String,
    val data: T? = null,
    val statusCode: Int = 0
)
