package no.kristiania.pg5100exam.utils

interface Mapper<D,E> {
    fun fromEntity(entity: E): D
    fun toEntity(domain: D): E
}