package no.kristiania.pg5100exam.controller

import no.kristiania.pg5100exam.dto.RegisterUserDTO
import no.kristiania.pg5100exam.model.UserEntity
import no.kristiania.pg5100exam.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/user")
class UserController(@Autowired private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody registerUserDTO: RegisterUserDTO): ResponseEntity<UserEntity> {
        val createdUser = userService.registerUser(registerUserDTO)
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register/user").toUriString()
        )
        return ResponseEntity.created(uri).body(createdUser)
    }

    @GetMapping("/all")
    fun getUsers(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getUsers())
    }
}