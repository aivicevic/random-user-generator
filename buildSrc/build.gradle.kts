// Used only to enable use of Config.kt values in other build scripts. DO NOT ADD ANY OTHER BUILD CODE HERE.
repositories {
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
}
