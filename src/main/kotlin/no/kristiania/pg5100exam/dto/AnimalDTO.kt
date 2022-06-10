package no.kristiania.pg5100exam.dto

data class AnimalDTO(
    val id: Long? = null,
    val name: String?,
    val type: String?,
    val breed: String?,
    val age: Int?,
    val health: String?
)