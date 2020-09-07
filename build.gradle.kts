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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
