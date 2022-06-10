package no.kristiania.pg5100exam.repo

import no.kristiania.pg5100exam.dto.AnimalDTO
import no.kristiania.pg5100exam.model.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimalRepo: JpaRepository<AnimalEntity, Long> {

 fun findByName(name: String): AnimalEntity

 fun findAnimalById(animalId: Long): AnimalEntity?

}