package no.kristiania.pg5100exam.integrationTest

import no.kristiania.pg5100exam.dto.RegisterUserDTO
import no.kristiania.pg5100exam.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserService::class)
class DatabaseIntegrationTest(@Autowired private val userService: UserService) {
    @Test
    fun createAndFindUserIntegrationTest(){
        userService.registerUser(RegisterUserDTO("gøy@.com", "pirate"))
        val createdUser = userService.loadUserByUsername("gøy@.com")
        assert(createdUser.username == "gøy@.com")
        assert(createdUser.authorities.first().authority == "USER")
    }

}