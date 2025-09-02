import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NetworkModulePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("convention.kotlin-module")
        pluginManager.apply(libs.findPlugin("kotlin-serialization").get().get().pluginId)

        dependencies {
            // Koin
            add("implementation", project.dependencies.platform(libs.findLibrary("koin-bom").get()))
            add("implementation", libs.findBundle("koin-common").get())

            // Serialization
            add("implementation", libs.findBundle("serialization-common").get())

            // Ktor
            add("implementation", libs.findBundle("ktor").get())

            // Modules
            add("implementation", project(":app:core:utils"))
        }
    }
}
