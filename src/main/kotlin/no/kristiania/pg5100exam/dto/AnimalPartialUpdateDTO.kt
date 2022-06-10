package no.kristiania.pg5100exam.dto

data class AnimalPartialUpdateDTO (
    val name: String? = null,
    val type: String? = null,
    val breed: String?,
    val age: Int? = null,
    val health: String? = null
)