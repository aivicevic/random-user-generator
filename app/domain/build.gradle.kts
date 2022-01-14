plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(AppDependencies.kotlinStdLib)

    implementationList(AppDependencies.roomLibs)
    kapt(AppDependencies.roomCompiler)

    api(AppDependencies.AndroidX.lifecycleRuntime)
    api(AppDependencies.AndroidX.lifecycleLiveDataKtx)
    kapt(AppDependencies.AndroidX.lifecycleCompiler)

    implementation(AppDependencies.moshi)

    // Testing
    testImplementationList(AppDependencies.TestLibs.AndroidX.testLibs)
    testImplementationList(AppDependencies.TestLibs.testLibs)
}

