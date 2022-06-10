package no.kristiania.pg5100exam.controller

import no.kristiania.pg5100exam.dto.AnimalDTO
import no.kristiania.pg5100exam.model.AnimalEntity
import no.kristiania.pg5100exam.service.ShelterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/shelter")
class ShelterController(@Autowired private val shelterService: ShelterService)  {

    @GetMapping("/all")
    fun getAllAnimals(): ResponseEntity<List<AnimalEntity>> {
        return ResponseEntity.ok().body(shelterService.getAllAnimals())
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun getAnimalById(@PathVariable id: Long): ResponseEntity<AnimalEntity>{
        return ResponseEntity.ok().body(shelterService.getAnimal(id))
    }

    @PostMapping("/add-animal")
    fun addNewAnimal(@RequestBody animalDTO: AnimalDTO): ResponseEntity<AnimalEntity> {
        val createdAnimal = shelterService.addNewAnimal(animalDTO)
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/add/animal").toUriString()
        )
        return ResponseEntity.created(uri).body(createdAnimal)
    }

    @PutMapping("/update-animal")
    fun updateAnimal(@RequestBody animalEntity: AnimalEntity): ResponseEntity<AnimalEntity> =
        ResponseEntity.ok(shelterService.updateAnimal(animalEntity))

   /* @PutMapping
    fun partialUpdateAnimal(@PathVariable id: Long, @RequestBody animalPartialUpdateDTO: AnimalPartialUpdateDTO): AnimalDTO {
         return animalService.updatePartial(id, partialUpdateAnimal.name, partialUpdateAnimal.age,
         partialUpdateAnimal.breed, partialUpdateAnimal.health, partialUpdateAnimal.type)
    }*/

    @DeleteMapping("/delete-animal/{id}")
    fun deleteAnimal(@PathVariable id: Long): ResponseEntity<Boolean> =
       ResponseEntity(shelterService.deleteAnimal(id), HttpStatus.NO_CONTENT)

}
