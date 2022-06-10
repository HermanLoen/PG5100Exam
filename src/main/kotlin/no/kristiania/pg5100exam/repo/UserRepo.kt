package no.kristiania.pg5100exam.repo

import no.kristiania.pg5100exam.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo:JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String?): UserEntity
}