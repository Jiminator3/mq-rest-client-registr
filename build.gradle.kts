import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("io.ebean") version "12.3.2"
	id("org.springframework.boot") version "2.5.0-RC1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.0-RC"
	kotlin("plugin.spring") version "1.5.0-RC"
	kotlin("plugin.jpa") version "1.5.0-RC"
}

group = "ru.jiminator"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	jcenter()
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	compileOnly("au.com.console:kassava:2.1.0")
	implementation("io.ebean:ebean:12.3.2")
	implementation("io.ebean:ebean-querybean:12.2.3")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.springframework.boot:spring-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
	implementation("org.hibernate:hibernate-core:5.4.31.Final") //:5.2.15.Final
	implementation("javax.xml.bind:jaxb-api") //:2.3.0
	implementation("com.github.docker-java:docker-java-transport-zerodep:3.2.8")
	compileOnly("org.springframework.boot:spring-boot-starter-webflux:2.5.3")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("io.rest-assured:rest-assured:4.4.0")
	testImplementation("io.rest-assured:json-schema-validator:4.1.2")
	testImplementation("io.ebean:ebean-test:12.3.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.hibernate:hibernate-testing")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("io.zonky.test:embedded-database-spring-test:2.0.0") //:2.0.0-beta1
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

