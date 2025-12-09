package dev.himanshu.noteTaking.repository

import dev.himanshu.noteTaking.entities.TagEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TagRepository : JpaRepository<TagEntity, UUID> {
    fun findByName(name: String): TagEntity?
    fun existsByName(name: String): Boolean
}