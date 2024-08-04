plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.kotlinpoet)
    implementation(libs.org.jetbrains.kotlin.multiplatform.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradlePlugin.android)
}

gradlePlugin {
    plugins {
        create("GenerateKeyValues") {
            id = "io.github.staakk.nptracker.generatekeyvalues"
            implementationClass = "io.github.staakk.nptracker.GenerateKeyValuesPlugin"
        }
    }
}