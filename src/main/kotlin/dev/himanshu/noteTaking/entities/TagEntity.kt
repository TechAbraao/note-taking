package dev.himanshu.noteTaking.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "tags")
data class TagEntity(

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", nullable = false, updatable = false)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val name: String = "",

    @CreatedDate @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate @Column(nullable = false)
    var updatedAt: LocalDateTime? = null
)
