import io.github.staakk.nptracker.GenerateKeyValuesTask

plugins {
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    alias(libs.plugins.kotlinxSerialization)
    id("io.github.staakk.nptracker.generatekeyvalues")
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain"))
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.json)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

tasks.register<GenerateKeyValuesTask>("generateSecrets") {
    inputFile = File(projectDir, "/secrets.properties")
    outputPackage = "io.github.staakk.nptracker"
    outputClassName = "Secrets"
}