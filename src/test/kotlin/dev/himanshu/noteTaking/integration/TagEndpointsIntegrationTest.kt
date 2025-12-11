package dev.himanshu.noteTaking.integration

import com.fasterxml.jackson.databind.ObjectMapper
import dev.himanshu.noteTaking.dto.TagDTO
import dev.himanshu.noteTaking.dto.request.TagRequest
import dev.himanshu.noteTaking.repository.TagRepository
import dev.himanshu.noteTaking.utils.ApiResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test
import kotlin.test.assertTrue

@SpringBootTest(properties = ["spring.profiles.active=test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class TagEndpointsIntegrationTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper,
    var tagRepository: TagRepository
) {

    @BeforeEach
    fun cleanDatabase() {
        tagRepository.deleteAll()
    }

    private fun performPostTags(name: String): ResultActions {
        val tagRequestCreation = TagRequest(name = name)
        return mockMvc.perform(
            post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagRequestCreation))
        )
    }
    private fun performGetAllTags(): ResultActions =
        mockMvc.perform(get("/api/tags")
            .contentType(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `Criar uma nova tag`() {
        val tagCreated = performPostTags("TagTest").andExpect(status().isCreated)

        tagCreated
            .andExpect(jsonPath("$.message").value("Tag created successfully."))
            .andExpect(jsonPath("$.data.name").value("TagTest"))
            .andExpect(jsonPath("$.data.id").exists())
    }

    @Test
    fun `Retornar uma lista de todas as tags`() {
        val request = performGetAllTags().andExpect(status().isOk)

        request
            .andExpect(
                jsonPath("$.message")
                    .value("All tags were successfully returned.")
            )
            .andExpect(
                jsonPath("$.statusCode")
                    .value(200)
            )

        val responseBody = request.andReturn().response.contentAsString
        val response = objectMapper.readValue(responseBody, ApiResponse::class.java)
        val tags: List<TagDTO> = objectMapper.convertValue(
            response.data,
            objectMapper.typeFactory.constructCollectionType(List::class.java,
                TagDTO::class.java))

        assertTrue(tags.all { it is TagDTO })
    }

    @Test
    fun `Deve converter o primeiro caractere da tag para maiúscula ao criar`() {
        val tagCreated = performPostTags("tagtest").andExpect(status().isCreated)

        tagCreated
            .andExpect(status().isCreated)
            .andExpect(
                jsonPath("$.message")
                    .value("Tag created successfully.")
            )
            .andExpect(
                jsonPath("$.data.name")
                    .value("Tagtest")
            )
    }

    @Test
    fun `Deve retornar erro ao tentar criar tag duplicada`() {
        val tagName: String = "TagTest"
        performPostTags(name = tagName).andExpect(status().isCreated)

        val tagCreated = performPostTags(tagName).andExpect(status().isConflict)

        tagCreated
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.message").value("Tag with name '${tagName}' already exists."))
    }
}
