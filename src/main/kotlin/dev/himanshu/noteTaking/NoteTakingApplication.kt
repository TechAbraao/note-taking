package dev.himanshu.noteTaking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class NoteTakingApplication

fun main(args: Array<String>) {
	runApplication<NoteTakingApplication>(*args)
}
