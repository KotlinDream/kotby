plugins {
    kotlin("jvm") version "1.5.30"
    id("org.sonarqube") version "3.3"
    jacoco
}

group = "info.dreamcoder"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("io.kotest:kotest-runner-junit5:${properties["kotestVersion"]}")
    testImplementation("io.kotest:kotest-assertions-core:${properties["kotestVersion"]}")
    testImplementation("io.kotest:kotest-property:${properties["kotestVersion"]}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(true)
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "KotlinDream_kotby")
        property("sonar.organization", "kotlin-dream")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}