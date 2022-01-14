buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(ProjectConfig.androidGradlePlugin)
        classpath(ProjectConfig.kotlinGradlePlugin)
        classpath(ProjectConfig.ktlintGradlePlugin)
        classpath(ProjectConfig.navigationSafeArgsPlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenCentral()
    }
}

plugins {
    id(ProjectConfig.ktlintPluginId) version Versions.ktlintVersion
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
