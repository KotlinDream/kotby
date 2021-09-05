plugins {
    kotlin("jvm") version "1.5.30"
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