package io.github.staakk.nptracker

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class GenerateKeyValuesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.afterEvaluate {
            target.kotlinExtension.sourceSets.named("commonMain").configure {
                kotlin {
                    srcDirs(
                        *tasks
                            .withType(GenerateKeyValuesTask::class)
                            .toTypedArray()
                    )
                }
            }
        }
    }
}