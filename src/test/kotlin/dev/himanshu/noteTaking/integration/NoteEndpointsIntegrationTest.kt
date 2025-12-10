package dev.himanshu.noteTaking.integration

import com.fasterxml.jackson.databind.ObjectMapper
import dev.himanshu.noteTaking.dto.request.NoteRequest
import dev.himanshu.noteTaking.repository.NoteRepository
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

    private fun createNote(payload: NoteRequest): ResultActions {
        TODO()
    }
}