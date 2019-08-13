plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(ProjectConfig.Android.compileSdkVersion)
    buildToolsVersion(ProjectConfig.Android.buildToolsVersion)

    defaultConfig {
        minSdkVersion(ProjectConfig.Android.minSdkVersion)
        targetSdkVersion(ProjectConfig.Android.targetSdkVersion)
        versionCode = ProjectConfig.Android.versionCode
        versionName = ProjectConfig.Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))

    // Kotlin
    implementation(ProjectConfig.Libs.kotlinStd)

    // Dependency Injection
    implementation(ProjectConfig.Libs.Dagger.dagger)
    kapt(ProjectConfig.Libs.Dagger.compiler)

    // LifeCycle Components
    implementation(ProjectConfig.Libs.AndroidX.LifeCycle.liveData)

    // Coroutines
    implementation(ProjectConfig.Libs.Coroutines.core)

    // Network
    implementation(ProjectConfig.Libs.Network.Retrofit.retrofit)

    // Room
    implementation(ProjectConfig.Libs.AndroidX.Room.runtime)
    implementation(ProjectConfig.Libs.AndroidX.Room.rxJava)
    kapt(ProjectConfig.Libs.AndroidX.Room.compiler)

    //// Testing
    // testImplementation(ProjectConfig.TestLibs.AndroidX.core)
    // testImplementation(ProjectConfig.TestLibs.AndroidX.coreArch)
    // testImplementation(ProjectConfig.TestLibs.AndroidX.rules)
    // testImplementation(ProjectConfig.TestLibs.AndroidX.jUnitExt)
    // testImplementation(ProjectConfig.TestLibs.mockito)
    // testImplementation(ProjectConfig.TestLibs.mockitoKt)
}