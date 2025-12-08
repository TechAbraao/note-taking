package dev.himanshu.noteTaking.entities
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "notes")
@EntityListeners(AuditingEntityListener::class)
data class NoteEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", nullable = false, updatable = false)
    val id: UUID? = null,

    @Column(nullable = false)
    @field:Size(max = 20)
    val title: String = "",

    @field:Size(max = 100)
    @Column(nullable = false)
    val description: String = "",

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime? = null
)
