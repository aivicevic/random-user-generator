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

    // Kotlin
    implementation(ProjectConfig.Libs.kotlinStd)

    // LifeCycle Components
    implementation(ProjectConfig.Libs.AndroidX.LifeCycle.extensions)

    // RxJava
    implementation(ProjectConfig.Libs.Rx.rxJava)
    implementation(ProjectConfig.Libs.Rx.rxAndroid)

    // Room
    implementation(ProjectConfig.Libs.AndroidX.Room.runtime)
    kapt(ProjectConfig.Libs.AndroidX.Room.compiler)

    //// Testing
    // testImplementation(Config.TestLibs.AndroidX.core)
    // testImplementation(Config.TestLibs.AndroidX.coreArch)
    // testImplementation(Config.TestLibs.AndroidX.rules)
    // testImplementation(Config.TestLibs.AndroidX.jUnitExt)
    // testImplementation(Config.TestLibs.mockito)
    // testImplementation(Config.TestLibs.mockitoKt)
}