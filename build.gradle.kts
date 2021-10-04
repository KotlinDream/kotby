plugins {
    kotlin("jvm") version "1.5.31"
    id("org.sonarqube") version "3.3"
    jacoco
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "info.dreamcoder"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.vertical-blank:sql-formatter:2.0.2")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("io.github.microutils:kotlin-logging:2.0.11")


    testImplementation("org.amshove.kluent:kluent:1.68")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(true)
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

sonarqube {
    properties {
        property("sonar.projectKey", "KotlinDream_kotby")
        property("sonar.organization", "kotlin-dream")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenKotlin") {
            from(components["java"])
            pom {
                name.set("Kotby")
                description.set("A kotlin library same as ruby")
                url.set("https://github.com/KotlinDream/kotby")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("jimxl")
                        name.set("jimxl")
                        email.set("tianxiaxl@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/KotlinDream/kotby")
                }
            }
        }
    }
}

if(System.getenv("JITPACK") != "true") {
    signing {
        sign(publishing.publications["mavenKotlin"])
    }

    tasks.javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }

    nexusPublishing {
        repositories {
            sonatype {
                nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
                snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
                username.set(System.getenv("SONATYPE_USERNAME"))
                password.set(System.getenv("SONATYPE_PASSWORD"))
            }
        }
    }
}

