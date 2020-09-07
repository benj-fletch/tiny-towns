import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    java
    kotlin("jvm") version "1.4.0"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // Logging things
    implementation("org.slf4j:slf4j-api:1.7.30")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")
    runtimeOnly("ch.qos.logback:logback-core:1.2.3")

    // testing dependencies:
    val jUnitVersion = "5.6.2"
    testImplementation("org.junit.jupiter:junit-jupiter:${jUnitVersion}")
    testImplementation("org.assertj:assertj-core:3.17.2")
}

repositories {
    mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
