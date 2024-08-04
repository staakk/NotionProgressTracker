package io.github.staakk.nptracker

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class GenerateKeyValuesTask @Inject constructor(
    private val layout: ProjectLayout,
) : DefaultTask() {

    @get:InputFile
    abstract val inputFile: Property<File>

    @get:Input
    var outputPackage: String = ""

    @get:Input
    var outputClassName: String = "KeyValues"

    @OutputDirectory
    val output: File = createOutputDir()

    @TaskAction
    fun action() {
        output.createNewFile()

        val properties = inputFile.get().readLines()
            .map { it.split("=") }
            .associate { it[0] to it[1] }

        generateKotlinFile(properties)
            .writeTo(output)
    }

    private fun createOutputDir(): File =
        layout.buildDirectory.dir("generated").get()
            .let { File(it.asFile, "keyvalues/kotlin/${outputPackageDir()}") }

    private fun outputPackageDir(): String =
        if (outputPackage.isNotEmpty())
            outputPackage.split('.').joinToString(separator = "/")
        else ""

    private fun generateKotlinFile(properties: Map<String, String>): FileSpec =
        FileSpec.builder(outputPackage, outputClassName)
            .addType(
                TypeSpec.objectBuilder(outputClassName)
                    .apply {
                        properties.forEach { (name, value) ->
                            addProperty(
                                PropertySpec.builder(name, String::class, KModifier.CONST)
                                    .initializer("%S", value)
                                    .build()
                            )
                        }
                    }
                    .build()
            )
            .build()
}