package dev.himanshu.noteTaking.repository
import dev.himanshu.noteTaking.entities.NoteEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NoteRepository : JpaRepository<NoteEntity, UUID> {
    fun findByTitle(title: String): NoteEntity?
    fun existsByTitle(title: String): Boolean
}