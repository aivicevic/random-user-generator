plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(ProjectConfig.Android.compileSdkVersion)
    buildToolsVersion(ProjectConfig.Android.buildToolsVersion)

    defaultConfig {
        applicationId = ProjectConfig.Android.applicationId
        minSdkVersion(ProjectConfig.Android.minSdkVersion)
        targetSdkVersion(ProjectConfig.Android.targetSdkVersion)
        versionCode = ProjectConfig.Android.versionCode
        versionName = ProjectConfig.Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":data"))
    implementation(project(":domain"))

    // Kotlin
    implementation(ProjectConfig.Libs.kotlinStd)
    implementation(ProjectConfig.Libs.AndroidX.Ktx.core)

    // Support
    implementation(ProjectConfig.Libs.AndroidX.Support.appCompat)
    implementation(ProjectConfig.Libs.AndroidX.Support.constraintLayout)
    implementation(ProjectConfig.Libs.AndroidX.Support.recyclerView)
    implementation(ProjectConfig.Libs.AndroidX.Support.swipeRefreshLayout)

    // LifeCycle Components
    implementation(ProjectConfig.Libs.AndroidX.LifeCycle.viewModelKtx)
    implementation(ProjectConfig.Libs.AndroidX.LifeCycle.liveData)
    kapt(ProjectConfig.Libs.AndroidX.LifeCycle.compiler)

    // Room
    implementation(ProjectConfig.Libs.AndroidX.Room.runtime)
    implementation(ProjectConfig.Libs.AndroidX.Room.rxJava)
    kapt(ProjectConfig.Libs.AndroidX.Room.compiler)

    // Dependency Injection
    implementation(ProjectConfig.Libs.Dagger.dagger)
    kapt(ProjectConfig.Libs.Dagger.compiler)
    implementation(ProjectConfig.Libs.Dagger.android)
    implementation(ProjectConfig.Libs.Dagger.androidSupport)
    kapt(ProjectConfig.Libs.Dagger.androidProcessor)

    // Coroutines
    implementation(ProjectConfig.Libs.Coroutines.core)
    implementation(ProjectConfig.Libs.Coroutines.android)

    // Network
    implementation(ProjectConfig.Libs.Network.Retrofit.retrofit)
    implementation(ProjectConfig.Libs.Network.Retrofit.rxJavaAdapter)
    implementation(ProjectConfig.Libs.Network.Retrofit.moshiConverter)
    implementation(ProjectConfig.Libs.Network.OkHttp.okHttp)
    implementation(ProjectConfig.Libs.Network.OkHttp.loggingInterceptor)

    // Misc.
    implementation(ProjectConfig.Libs.material)
    implementation(ProjectConfig.Libs.glide)

    // Testing
    testImplementation(ProjectConfig.TestLibs.AndroidX.core)
    testImplementation(ProjectConfig.TestLibs.AndroidX.coreArch)
    testImplementation(ProjectConfig.TestLibs.AndroidX.rules)
    testImplementation(ProjectConfig.TestLibs.AndroidX.jUnitExt)
    testImplementation(ProjectConfig.TestLibs.mockito)
    testImplementation(ProjectConfig.TestLibs.mockitoKt)
    testImplementation(ProjectConfig.TestLibs.coroutinesTest)
    androidTestImplementation(ProjectConfig.TestLibs.AndroidX.runner)
    androidTestImplementation(ProjectConfig.TestLibs.AndroidX.espresso)
    androidTestUtil(ProjectConfig.TestLibs.AndroidX.orchestrator)
}