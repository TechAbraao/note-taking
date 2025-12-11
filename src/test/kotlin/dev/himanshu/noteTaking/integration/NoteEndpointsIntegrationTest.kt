package dev.himanshu.noteTaking.integration

import com.fasterxml.jackson.databind.ObjectMapper
import dev.himanshu.noteTaking.dto.NoteDTO
import dev.himanshu.noteTaking.dto.request.NoteRequest
import dev.himanshu.noteTaking.mappers.NoteMapper
import dev.himanshu.noteTaking.repository.NoteRepository
import dev.himanshu.noteTaking.utils.Priority
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@SpringBootTest(properties = ["spring.profiles.active=test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class NoteEndpointsIntegrationTest @Autowired constructor (
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper,
    var noteRepository: NoteRepository
) {
    @BeforeEach
    fun cleanDatabase() {
        noteRepository.deleteAll()
    }

    private fun performPostNotes(payload: NoteRequest): ResultActions {
        return mockMvc.perform(
            post("/api/notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(payload))
        )
    }

    @Test
    fun `Criar uma nova nota`() {
        val payloadRequest: NoteRequest = NoteRequest(
            title = "Título da Nota",
            description = "Descrição da Nota",
            priority = Priority.MEDIUM,
            tags = listOf("Ficção", "História")
        )
        val request = performPostNotes(payloadRequest)
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.message").value("Note created successfully."))
            .andExpect(jsonPath("$.statusCode").value(201))

    }

    @Test
    fun `Verificar se está persistindo apesar do campo 'tag' estar nulo`() {
        val payloadRequest: NoteRequest = NoteRequest(
            title = "Título da Nota",
            description = "Descrição da Nota",
            priority = Priority.MEDIUM,
            tags = listOf()
        )

        val request = performPostNotes(payloadRequest)
            .andExpect(status().isBadRequest)
    }
}