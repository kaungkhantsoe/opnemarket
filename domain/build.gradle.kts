plugins {
    id(libs.plugins.javaLibrary.get().pluginId)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
}

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)

    testImplementation(libs.bundles.unitTest)
}

kover {
    currentProject {
        createVariant("custom") {
            add("jvm")
        }
    }
}
