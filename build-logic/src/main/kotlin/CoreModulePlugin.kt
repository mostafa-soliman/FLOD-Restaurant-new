import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CoreModulePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("convention.kotlin-module")
        pluginManager.apply(libs.findPlugin("kotlin-serialization").get().get().pluginId)

        dependencies {
            // Koin
            add("api", project.dependencies.platform(libs.findLibrary("koin-bom").get()))
            add("api", libs.findBundle("koin-common").get())
            
            // Serialization
            add("api", libs.findBundle("serialization-common").get())
            
            // Modules
            add("api", project(":app:core:network"))
            add("api", project(":app:core:utils"))
        }
    }
}
