pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}


    // Add this section to enable version catalogs
//    versionCatalogs {
//        create("libs") {
//            from(files("gradle/libs.versions.toml")) // Path to your version catalog file
//        }
//    }


rootProject.name = "PetkuMobile"
include(":app")
