plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.himanshu"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

// Aqui você adiciona os Starters do Spring Boot
dependencies {
    // APIs REST
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JPA + Hibernate (necessário para datasource, entity manager, etc.)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Driver do PostgreSQL
    runtimeOnly("org.postgresql:postgresql")

    // Validação (Jakarta + Hibernate Validator)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // JSON + Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Reflexão do Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Testes
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Kotlin Test extensions
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Se usar JPA/H2 para testes em memória
    testImplementation("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
