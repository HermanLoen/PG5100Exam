package no.kristiania.pg5100exam.unitTest.controller

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.controller.ShelterController
import no.kristiania.pg5100exam.model.AnimalEntity
import no.kristiania.pg5100exam.service.ShelterService
import no.kristiania.pg5100exam.service.UserService
import org.hamcrest.Matchers.*
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@WebMvcTest(ShelterController::class)
@AutoConfigureMockMvc(addFilters = false)
class ShelterControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig{
        @Bean
        fun shelterService() = mockk<ShelterService>()
        @Bean
        fun userService() = mockk<UserService>()

    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var shelterService: ShelterService

    private val mockAnimal: AnimalEntity = AnimalEntity(id = 1, name = "bigbear", type = "brun", breed = "idk", age = 24, health = "Healthy")


    @Test
    fun shouldListAllAnimalsEndpointTest() {
        every { shelterService.getAllAnimals() } answers {
            mutableListOf(
                AnimalEntity(id = 1, name = "bjørn", type = "brun", breed = "idk", age = 3, health = "Healthy"),
                AnimalEntity(id = 2, name = "ompalompa", type = "monster", breed = "idk", age = 3, health = "Healthy")
            )
        }
        mockMvc.get("/api/shelter/all") {
        }
            .andExpect { status { isOk() } }
            .andExpect { content { jsonPath("$[0].name", `is`("bjørn")) } }
            .andExpect { content { jsonPath("$[1].name", `is`("ompalompa")) } }
    }

    @Test
    fun shouldGetAnimalByIdEndpointTest() {
        every { shelterService.getAnimal(any()) } answers {mockAnimal}
        mockMvc.get("/api/shelter/1"){}
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.id", `is`(1)) }
            .andExpect { jsonPath("$.name", `is`("bigbear")) }
            .andExpect { jsonPath("$.type", `is`("brun")) }
            .andExpect { jsonPath("$.breed", `is`("idk")) }
        every { shelterService.getAnimal(any()) } answers { null }
        mockMvc.get("/api/shelter/1"){}
            .andExpect { content { empty<String>() } }

    }

    @Test
    fun shouldAddAnimalEndpointTest() {
        val jsonTestAnimalEntity = JSONObject(mapOf("name" to "bigbear", "type" to "bear", "breed" to "idk", "age" to 4, "health" to "healty"))
        every { shelterService.addNewAnimal(any()) } answers {mockAnimal}
        mockMvc.post("/api/shelter/add-animal"){
            contentType = MediaType.APPLICATION_JSON
            content = jsonTestAnimalEntity
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { jsonPath("$.name", `is`("bigbear")) } }
    }

    @Test
    fun shouldUpdateAnimalEndpointTest(){
        val jsonTestAnimalEntity = JSONObject(mapOf("name" to "bigbear", "type" to "bear", "breed" to "idk", "age" to 4, "health" to "healty"))
        every { shelterService.updateAnimal(any()) } answers {firstArg()}
        mockMvc.put("/api/shelter/update-animal"){
            contentType = MediaType.APPLICATION_JSON
            content = jsonTestAnimalEntity
        }
            .andExpect { status { isOk() } }
            .andExpect { content { jsonPath("$.name", `is`("bigbear")) } }
    }

    @Test
    fun shouldDeleteAnimalEndpointTest() {
        every { shelterService.deleteAnimal(any()) } answers {true}
        mockMvc.delete("/api/shelter/delete-animal/1"){}
            .andExpect { status { isNoContent() } }
    }
}