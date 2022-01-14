/**
 * Dependency paths for all modules
 */
object AppDependencies {

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"

    object AndroidX {

        private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
        private const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}"
        private const val workRuntimeKtx =
            "androidx.work:work-runtime-ktx:${Versions.workRuntimeKtx}"

        val ktxLibs = arrayListOf(coreKtx, lifecycleRuntimeKtx, workRuntimeKtx)

        /**
         * Components/Views
         */
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayoutVersion}"

        /**
         * Lifecycle
         */
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime:${Versions.lifeCycleVersion}"
        const val lifecycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleVersion}"
        const val lifecycleLiveDataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycleVersion}"
        const val lifecycleCompiler =
            "androidx.lifecycle:lifecycle-compiler:${Versions.lifeCycleVersion}"

        /**
         * Navigation
         */
        private const val navigationKtx =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
        private const val navigationUi =
            "androidx.navigation:navigation-ui:${Versions.navigationVersion}"

        val navigationLibs = arrayListOf(navigationKtx, navigationUi)
    }

    /**
     * Coroutines
     */
    private const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesCoreVersion}"
    private const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutinesAndroidVersion}"

    val coroutinesLibs = arrayListOf(coroutinesCore, coroutinesAndroid)

    /**
     * Koin
     */
    private const val koinAndroid = "io.insert-koin:koin-android:${Versions.koinVersion}"
    private const val koinAndroidCompat =
        "io.insert-koin:koin-android-compat:${Versions.koinVersion}"
    private const val koinAndroidXWorkManager =
        "io.insert-koin:koin-androidx-workmanager:${Versions.koinVersion}"
    private const val koinAndroidXCompose =
        "io.insert-koin:koin-androidx-compose:${Versions.koinVersion}"

    val koinLibs = arrayListOf(
        koinAndroid,
        koinAndroidCompat,
        koinAndroidXWorkManager,
        koinAndroidXCompose
    )

    /**
     * Room
     */
    private const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    val roomLibs = arrayListOf(roomRuntime, roomKtx)

    /**
     * Network
     */
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    private const val moshiConverter =
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshiVersion}"
    private const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"

    private const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    private const val okhttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"

    val networkLibs =
        arrayListOf(retrofit, moshiConverter, moshi, moshiKotlin, okhttp, okhttpLoggingInterceptor)

    /**
     * Misc
     */
    private const val material = "com.google.android.material:material:${Versions.materialVersion}"
    private const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"

    val miscLibs = arrayListOf(material, glide)

    object TestLibs {

        object AndroidX {

            const val orchestrator = "androidx.test:orchestrator:${Versions.orchestratorVersion}"

            /**
             * Testing
             */
            private const val core = "androidx.test:core:${Versions.coreTestVersion}"
            private const val coreArch =
                "androidx.arch.core:core-testing:${Versions.coreArchTestVersion}"
            private const val rules = "androidx.test:rules:${Versions.rulesVersion}"
            private const val jUnitExt = "androidx.test.ext:junit:${Versions.junitExtVersion}"
            private const val truthExt = "androidx.test.ext:truth:${Versions.truthExtVersion}"

            val testLibs = arrayListOf(core, coreArch, rules, jUnitExt, truthExt)

            /**
             * Android Testing
             */
            private const val espressoCore =
                "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
            private const val runner = "androidx.test:runner:${Versions.runnerVersion}"

            val androidTestLibs = arrayListOf(espressoCore, runner)
        }

        private const val mockito = "org.mockito:mockito-core:${Versions.mockitoVersion}"
        private const val mockitoKt =
            "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKtVersion}"
        private const val koinTest = "io.insert-koin:koin-test:${Versions.koinVersion}"
        private const val koinJunit4 = "io.insert-koin:koin-test-junit4:${Versions.koinVersion}"
        private const val googleTruth = "com.google.truth:truth:${Versions.googleTruthVersion}"

        val testLibs = arrayListOf(mockito, mockitoKt, koinTest, koinJunit4, googleTruth)
    }
}
