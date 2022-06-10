package no.kristiania.pg5100exam.service

import no.kristiania.pg5100exam.dto.AnimalDTO
import no.kristiania.pg5100exam.model.AnimalEntity
import no.kristiania.pg5100exam.repo.AnimalRepo
import no.kristiania.pg5100exam.utils.AnimalMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShelterService(
    @Autowired private val animalRepo: AnimalRepo, private val animalMapper: AnimalMapper) {

    fun getAllAnimals(): List<AnimalEntity>? {
        return animalRepo.findAll()
    }

    fun getAnimal(id: Long): AnimalEntity? {
        return animalRepo.getById(id)
    }


    fun addNewAnimal(animalDTO: AnimalDTO): AnimalEntity {
        val newAnimal = AnimalEntity(name = animalDTO.name, type = animalDTO.type, breed = animalDTO.breed, age = animalDTO.age, health = animalDTO.health )
        return animalRepo.save(newAnimal)
    }


    fun deleteAnimal(id: Long): Boolean {
        if (animalRepo.existsById(id)){
            animalRepo.deleteById(id)
            return true
        }
        return false
    }

    fun updateAnimal(animalEntity: AnimalEntity): AnimalEntity? {
       animalEntity.id?.let { val exists = animalRepo.existsById(it)
            if (!exists)
                throw Exception("Animal with id ${animalEntity.id} is not present")
           animalRepo.save(animalEntity)
           return animalEntity
       }
        return null
    }
}
