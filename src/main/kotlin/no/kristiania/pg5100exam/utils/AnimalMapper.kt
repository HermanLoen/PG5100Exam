package no.kristiania.pg5100exam.utils

import no.kristiania.pg5100exam.dto.AnimalDTO
import no.kristiania.pg5100exam.model.AnimalEntity
import org.springframework.stereotype.Service

@Service
class AnimalMapper: Mapper<AnimalDTO, AnimalEntity> {
    override fun fromEntity(entity: AnimalEntity): AnimalDTO = AnimalDTO(
        entity.id,
        entity.name,
        entity.type,
        entity.breed,
        entity.age,
        entity.health

    )

    override fun toEntity(domain: AnimalDTO): AnimalEntity = AnimalEntity(
        domain.id,
        domain.name,
        domain.type,
        domain.breed,
        domain.age,
        domain.health
    )
}