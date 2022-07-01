plugins {
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
}

dependencies {
    // Local things
    implementation(project(":model"))

    // Ktor things
    val ktorVersion = "1.6.4"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")

    // Json things
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    // Cloud things
    implementation("com.google.cloud:google-cloud-firestore:3.0.3")

    // Logging things
    val logbackVersion = "1.2.6"
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // testing dependencies:
    val jUnitVersion = "5.6.2"
    testImplementation("org.junit.jupiter:junit-jupiter:${jUnitVersion}")
    testImplementation("org.assertj:assertj-core:3.17.2")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
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
