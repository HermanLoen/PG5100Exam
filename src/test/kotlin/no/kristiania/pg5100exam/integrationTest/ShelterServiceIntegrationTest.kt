package no.kristiania.pg5100exam.integrationTest

import no.kristiania.pg5100exam.dto.AnimalDTO
import no.kristiania.pg5100exam.repo.AnimalRepo
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc(addFilters = false)
class ShelterServiceIntegrationTest(@Autowired private var animalRepo: AnimalRepo) {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun addAndRetrieveAnimalIntegrationTest(){
        var animalDTO = AnimalDTO(name = "BigBadWolf", type = "Wolverine", age = 2, breed = "wolf", health = "healthy")
            mockMvc.post("/api/shelter/add-animal"){
            contentType = MediaType.APPLICATION_JSON
            content = JSONObject(mapOf("name" to "BigBadWolf", "type" to "Wolverine", "age" to 2, "breed" to "wolf", "health" to "healthy"))
        }
        val newAnimal = animalRepo.findAnimalById(1)
        assert(animalDTO.name == newAnimal?.name)
    }
}