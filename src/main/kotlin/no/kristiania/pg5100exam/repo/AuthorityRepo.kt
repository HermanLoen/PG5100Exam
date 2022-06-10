package no.kristiania.pg5100exam.repo

import no.kristiania.pg5100exam.model.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepo: JpaRepository<AuthorityEntity, Long> {

    fun findByAuthorityName(authorityName: String): AuthorityEntity
}