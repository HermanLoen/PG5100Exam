package no.kristiania.pg5100exam.unitTest.controller

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.controller.UserController
import no.kristiania.pg5100exam.dto.RegisterUserDTO
import no.kristiania.pg5100exam.model.UserEntity
import no.kristiania.pg5100exam.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(UserController::class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        /* @Bean
         fun authService() = mockk<AuthService>()*/

        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllUsersTest() {
        val userHerman = UserEntity(email = "herman@.com", password = "herm")
        every { userService.getUsers() } answers {
            mutableListOf(userHerman)
        }

        mockMvc.get("/api/user/all") {

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

    @Test
    fun testRegisterEndpoint(){
        every { userService.registerUser(RegisterUserDTO("jim@bob.com", "pirate")) } answers {
            val user = UserEntity(id = 1, email = "jim@bob.com", password = BCryptPasswordEncoder().encode("pirate"))
            user
        }

        mockMvc.post("/api/user/register"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\"email\":\"jim@bob.com\", \"password\":\"pirate\"}"
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }
}