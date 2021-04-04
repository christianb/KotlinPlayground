import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://github.com/JetBrains/kotlin/blob/master/ChangeLog.md
    kotlin("jvm") version "1.4.30"
    application
}

group = "com.playground"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://github.com/Kotlin/kotlinx.coroutines/blob/master/CHANGES.md
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}