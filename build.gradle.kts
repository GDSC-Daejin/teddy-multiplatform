plugins {
    id("com.android.application") version "7.0.4" apply false
    id("com.android.library") version "7.0.4" apply false

    val kotlinVersion = "1.6.10"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("android") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false

}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Google.dagger.hilt.android.gradlePlugin)
    }
}

group = "app.harry.teddy"
version = "1.0.00"