package no.kristiania.pg5100exam.unitTest.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100exam.dto.AnimalDTO
import no.kristiania.pg5100exam.model.AnimalEntity
import no.kristiania.pg5100exam.repo.AnimalRepo
import no.kristiania.pg5100exam.service.ShelterService
import no.kristiania.pg5100exam.utils.AnimalMapper
import org.junit.Test

class ShelterServiceUnitTest {

    private val animalRepo = mockk<AnimalRepo>()
    private val shelterService = ShelterService(animalRepo, animalMapper = AnimalMapper())


    @Test
    fun getAnimalTest() {
        val animalBjørn = AnimalEntity(id = 1, name = "bjørn", type = "brun", breed = "idk", age = 3, health = "Healthy")
        val animalTiger = AnimalEntity(id = 2, name = "tiger", type = "orange", breed = "idk", age = 5, health = "Healthy")

        every { animalRepo.findAll() } answers {
            mutableListOf(animalBjørn, animalTiger)
        }

        val shelter = shelterService.getAllAnimals()
        assert(shelter?.size == 2)
        assert(shelter?.first { it.name == "bjørn" }?.age == 3)

    }

    @Test
    fun getAllAnimals() {
        val animal1 = AnimalEntity(name = "Moose", type = "horse", breed = "idk", age = 4, health = "sick")
        val animal2 = AnimalEntity(name = "cow", type = "horse", breed = "idk", age = 4, health = "sick")

        every { animalRepo.findAll() } answers {
            mutableListOf(animal1, animal2)
        }

        val animals = shelterService.getAllAnimals()
        assert(animals?.size == 2)

    }

    @Test
    fun shouldAddNewAnimalTest(){
        every { animalRepo.save(any()) } answers {
            firstArg()
        }
        every { animalRepo.findAnimalById(any()) } answers {
            AnimalEntity(id = 1, name = "bjørn", type = "brun", breed = "idk", age = 3, health = "Healthy")
        }

        val addedAnimal = shelterService.addNewAnimal(AnimalDTO(id = 1, name = "bjørn", type = "brun", breed = "idk", age = 3, health = "Healthy"))
        assert(addedAnimal.name == "bjørn")
        assert(addedAnimal.type == "brun")

    }

    @Test
    fun shouldDeleteAnimalTest() {
        every { animalRepo.deleteById(any()) } answers {}
        every { animalRepo.existsById(any()) } answers  { true }
        assert(shelterService.deleteAnimal(1))
        every { animalRepo.existsById(any()) } answers {false}
        assert(!shelterService.deleteAnimal(1))
    }

    @Test
    fun shouldUpdateAnimalTest(){
        val animal = AnimalEntity(id = 1, name = "tiger", type = "orange", breed = "idk", age = 5, health = "Healthy")
        val animalWithNoID = AnimalEntity(name = "tiger", type = "orange", breed = "idk", age = 6, health = "Healthy")

        every { animalRepo.existsById(any()) } answers {
            true
        }
        every { animalRepo.save(any()) } answers {
            firstArg()
        }
        val animalUpdated = shelterService.updateAnimal(animal)
        assert(animalUpdated?.id == animal.id)
        assert(animalUpdated?.name == animal.name)
        assert(animalUpdated?.type == animal.type)
        every { animalRepo.existsById(any()) } answers {false}
        assert(shelterService.updateAnimal(animalWithNoID) == null)

    }
}