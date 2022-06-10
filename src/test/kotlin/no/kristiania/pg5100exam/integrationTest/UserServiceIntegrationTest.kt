package no.kristiania.pg5100exam.integrationTest

import no.kristiania.pg5100exam.dto.RegisterUserDTO
import no.kristiania.pg5100exam.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc(addFilters = true)
class UserServiceIntegrationTest(@Autowired private  var userService: UserService) {

    @Test
    fun registerAndSearchForUserIntegrationTest(){
        val registerUserDTO = RegisterUserDTO("herm@.com", "herm")
        val userCreated = userService.registerUser(registerUserDTO)
        assert(userCreated?.email == "herm@.com")
        val newUser = userService.loadUserByUsername("herm@.com")
        assert(userCreated?.email == newUser.username)
    }
}