pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // ✅ Recommended over FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
        // jcenter() // ⚠️ Optional: Uncomment if older dependencies require it
    }
}

rootProject.name = "NEWS APP"
include(":app")
