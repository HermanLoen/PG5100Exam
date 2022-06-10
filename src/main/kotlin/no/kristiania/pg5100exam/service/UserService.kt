package no.kristiania.pg5100exam.service

import no.kristiania.pg5100exam.dto.RegisterUserDTO
import no.kristiania.pg5100exam.model.AuthorityEntity
import no.kristiania.pg5100exam.model.UserEntity
import no.kristiania.pg5100exam.repo.AuthorityRepo
import no.kristiania.pg5100exam.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepo: UserRepo, @Autowired private val authorityRepo: AuthorityRepo): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
       username?.let{
           val user = userRepo.findByEmail(it)
           return User(user?.email, user?.password, user?.authorities?.map { authorityEntity ->  SimpleGrantedAuthority(authorityEntity.authorityName) } )
       }
        throw Exception("Forbidden")
    }

    fun getUsers(): List<UserEntity> {
        return userRepo.findAll()
    }

    fun registerUser(registerUser: RegisterUserDTO): UserEntity {
        val newUser = UserEntity(email = registerUser.email, password = BCryptPasswordEncoder().encode(registerUser.password))
        newUser.authorities.add(AuthorityEntity(1,"USER"))

        return userRepo.save(newUser)
    }

    fun createAuthority(authorityName: AuthorityEntity): AuthorityEntity {
        return authorityRepo.save(authorityName)
    }

    fun grantAuthorityToUser(email: String?, authorityName: String?): UserEntity? {
        val user = userRepo.findByEmail(email)
        authorityName?.let {
            val authority = authorityRepo.findByAuthorityName(it)
            user.authorities.add(authority)
            return userRepo.save(user)
        }
        return null
    }

    fun getAuthorities(): List<String> {
        return authorityRepo.findAll().map { it.authorityName }
    }


}
