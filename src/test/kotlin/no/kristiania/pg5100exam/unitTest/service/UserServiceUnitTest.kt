package no.kristiania.pg5100exam.unitTest.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.dto.RegisterUserDTO
import no.kristiania.pg5100exam.model.AuthorityEntity
import no.kristiania.pg5100exam.model.UserEntity
import no.kristiania.pg5100exam.repo.AuthorityRepo
import no.kristiania.pg5100exam.repo.UserRepo
import no.kristiania.pg5100exam.service.UserService
import org.junit.Test
import java.time.LocalDateTime


class UserServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val authorityRepo = mockk<AuthorityRepo>()
    private val userService = UserService(userRepo, authorityRepo)


    @Test
    fun getUserTest(){
        val userJonas = UserEntity(email = "jonas@.com", password = "pirate")
        val userHerman = UserEntity(email = "herman@.com", password = "pirate")

        every { userRepo.findAll() } answers {
            mutableListOf(userHerman, userJonas)
        }
        val users = userService.getUsers()
        assert(users.size == 2)
        assert(users.first {it.email == "jonas@.com"}.password == "pirate")
    }

    @Test
    fun shouldRegisterNewUserTest(){
        every { userRepo.save(any()) } answers {
            firstArg()
        }

        every { authorityRepo.findByAuthorityName(any()) } answers {
            AuthorityEntity(authorityName = "USER")
        }

        val createdUser = userService.registerUser(RegisterUserDTO("christine@.com", "cool"))
        assert(createdUser.email == "christine@.com")
        createdUser.enabled?.let { assert(it) }
    }

    @Test
    fun grantAuthorityToUserTest(){

        every { authorityRepo.findByAuthorityName(any()) } returns AuthorityEntity(1, "ADMIN")
        every { userRepo.findByEmail(any()) } answers {
            val user = UserEntity(1, "jim@bob.com", "pirate", LocalDateTime.now(), true )
            user.authorities.add(AuthorityEntity(1, "ADMIN"))
            user
        }

        every { userRepo.save(any()) } answers {
            val user = UserEntity(1, "jim@bob.com", "pirate", LocalDateTime.now(), true )
            user.authorities.add(AuthorityEntity(1, "ADMIN"))
            user
        }

        val createdUser = userService.registerUser(RegisterUserDTO("jim@bob.com", "pirate"))
        userService.grantAuthorityToUser(createdUser.email, "ADMIN")
        assert(userRepo.findByEmail("jim@bob.com").authorities[0].authorityName == "ADMIN")

    }

    @Test
    fun createAuthority(){
        every { authorityRepo.save(any()) } answers {
            firstArg()
        }
        val createdAuthority = userService.createAuthority(AuthorityEntity(authorityName = "ADMIN"))
        assert(createdAuthority.authorityName == "ADMIN")
    }

}