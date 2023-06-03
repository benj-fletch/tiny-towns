plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"
}

dependencies {
    // Serialisation things
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // testing dependencies:
    val jUnitVersion = "5.6.2"
    testImplementation("org.junit.jupiter:junit-jupiter:${jUnitVersion}")
    testImplementation("org.assertj:assertj-core:3.17.2")
}

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
