plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
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

    buildFeatures {
        dataBinding = true
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

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}

dependencies {
    implementation(project(":app:domain"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(AppDependencies.kotlinStdLib)
    implementationList(AppDependencies.AndroidX.ktxLibs)

    implementation(AppDependencies.AndroidX.appCompat)
    implementation(AppDependencies.AndroidX.swipeRefreshLayout)

    implementation(AppDependencies.AndroidX.lifecycleViewModelKtx)
    kapt(AppDependencies.AndroidX.lifecycleCompiler)

    implementationList(AppDependencies.AndroidX.navigationLibs)

    implementationList(AppDependencies.koinLibs)

    implementationList(AppDependencies.coroutinesLibs)

    implementationList(AppDependencies.miscLibs)

    // Testing
    testImplementationList(AppDependencies.TestLibs.AndroidX.testLibs)
    testImplementationList(AppDependencies.TestLibs.testLibs)
    androidTestImplementationList(AppDependencies.TestLibs.AndroidX.androidTestLibs)
    androidTestUtil(AppDependencies.TestLibs.AndroidX.orchestrator)
}
