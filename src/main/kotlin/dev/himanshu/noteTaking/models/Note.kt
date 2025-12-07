package dev.himanshu.noteTaking.models

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
)
