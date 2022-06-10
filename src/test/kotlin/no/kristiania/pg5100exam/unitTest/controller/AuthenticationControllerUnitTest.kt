package no.kristiania.pg5100exam.unitTest.controller

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.controller.AuthenticationController
import no.kristiania.pg5100exam.controller.UserController
import no.kristiania.pg5100exam.model.AuthorityEntity
import no.kristiania.pg5100exam.model.UserEntity
import no.kristiania.pg5100exam.service.UserService
import org.hamcrest.Matchers.`is`
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.time.LocalDateTime

@WebMvcTest(controllers = [AuthenticationController::class, UserController::class])
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()

    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userService: UserService


    private val mockUser: UserEntity = UserEntity(id = 1, email = "herm@.com", password = "pirate", created = LocalDateTime.now(), enabled = true)


    @Test
    fun testAuthorityAllEndpoint() {
        every { userService.getAuthorities() } answers {
            listOf("ADMIN", "USER")
        }

        mockMvc.get("/api/authentication/all") {
        }
            .andExpect { status { isOk() } }
            .andExpect {
                jsonPath("$") { isArray() }
            }
    }

    @Test
    fun shouldRegisterUserEndpointTest() {
        val jsonTestUserDTO = JSONObject(mapOf("email" to "herm@.com", "password" to "pirate"))
        every { userService.registerUser(any()) } answers {mockUser}
        mockMvc.post("/api/user/register"){
            contentType = MediaType.APPLICATION_JSON
            content = jsonTestUserDTO
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { jsonPath("$.email", `is`("herm@.com")) } }
    }

    @Test
    fun addAuthorityToUser(){
        val jsonTestUserDTO = JSONObject(mapOf("email" to "herman@.com", "authorityName" to "USER"))
        every { userService.createAuthority(any()) } answers {firstArg()}
        every { userService.grantAuthorityToUser(any(), any()) } answers { UserEntity(
            authorities = mutableListOf<AuthorityEntity>(AuthorityEntity(authorityName = "USER")), email = "", password = "") }
        mockMvc.post("/api/authentication/add"){
            contentType = MediaType.APPLICATION_JSON
            content = jsonTestUserDTO
        }
            .andExpect { status { isOk() } }
            .andExpect { content { jsonPath("$.authorities[0].authorityName", `is`("USER")) } }
    }

}