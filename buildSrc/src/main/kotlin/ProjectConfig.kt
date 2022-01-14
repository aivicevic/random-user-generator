/**
 * Project configuration settings
 */
@SuppressWarnings("SpellCheckingInspection")
object ProjectConfig {

    /**
     * Plugin class paths
     */
    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.androidGradleVersion}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val ktlintGradlePlugin = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlintVersion}"
    const val navigationSafeArgsPlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"

    /**
     * Plugin IDs
     */
    const val ktlintPluginId = "org.jlleitschuh.gradle.ktlint"
}
