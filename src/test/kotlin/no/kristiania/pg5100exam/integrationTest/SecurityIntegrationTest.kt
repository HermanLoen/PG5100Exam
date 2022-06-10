package no.kristiania.pg5100exam.integrationTest

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
@AutoConfigureMockMvc(addFilters = true)
class SecurityIntegrationTest{
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldLoginGivenValidUser() {
        mockMvc.post("/api/authentication"){
            contentType = MediaType.APPLICATION_JSON
            content = JSONObject(mapOf("email" to "admin@admin.com", "password" to "pirate"))
        }
            .andExpect { cookie { exists("access_token") } }
    }
}