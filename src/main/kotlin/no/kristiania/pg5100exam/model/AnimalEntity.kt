package no.kristiania.pg5100exam.model

import javax.persistence.*
//1627d09c-32a2-4cc3-942b-eedcd5d60b8f
@Entity
@Table(name = "animals")
class AnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animals_animal_id_seq")
    @SequenceGenerator(
        name = "animals_animal_id_seq",
        allocationSize = 1
    )
    @Column(name = "animal_id")
    val id: Long? = null,

    @Column(name = "animal_name")
    val name: String?,

    @Column(name = "animal_type")
    val type: String?,

    @Column(name = "animal_breed")
    val breed: String? = null,

    @Column(name = "animal_age")
    val age: Int? = null,

    @Column(name = "animal_health")
    val health: String? = null,
) {
    override fun toString(): String {
        return "AnimalEntity(id=$id, name='$name', type='$type', breed=$breed, age=$age, health=$health)"
    }
}