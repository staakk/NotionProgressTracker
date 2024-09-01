plugins {
    alias(libs.plugins.kotlinxSerialization)
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
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
            api(libs.kotlinx.datetime)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}