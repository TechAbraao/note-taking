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
    // Starter para desenvolver APIs REST
	implementation("org.springframework.boot:spring-boot-starter-web")

    // Starter de Spring Validator (Jakarta + Hibernate)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Suporte a JSON com Kotlin (JACKSON)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Reflexão do Kotlin (necessário pro Spring gerenciar beans Kotlin)
	implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Dependências para testes
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
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
