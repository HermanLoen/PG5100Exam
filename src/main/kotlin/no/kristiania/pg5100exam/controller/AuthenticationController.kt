package no.kristiania.pg5100exam.controller

import no.kristiania.pg5100exam.dto.AuthorityToUser
import no.kristiania.pg5100exam.model.AuthorityEntity
import no.kristiania.pg5100exam.model.UserEntity
import no.kristiania.pg5100exam.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/authentication")
class AuthenticationController(@Autowired private val userService: UserService) {

    @GetMapping("/all")
    fun getAuthorities(): ResponseEntity<List<String?>> {
        return ResponseEntity.ok().body(userService.getAuthorities())
    }

    @PostMapping("/create")
    fun createAuthority(@RequestBody authority: AuthorityEntity): ResponseEntity<AuthorityEntity> {
        val authorityEntity = userService.createAuthority(authority)
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/authentication/create").toUriString()
        )
        return ResponseEntity.created(uri).body(authorityEntity)
    }

    @PostMapping("/add")
    fun addAuthorityToUser(@RequestBody authorityToUser: AuthorityToUser): ResponseEntity<UserEntity> {
        return ResponseEntity.ok(userService.grantAuthorityToUser(authorityToUser.email, authorityToUser.authority))
    }

}
