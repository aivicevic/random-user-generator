buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(ProjectConfig.BuildPlugins.androidGradle)
        classpath(ProjectConfig.BuildPlugins.kotlinGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}