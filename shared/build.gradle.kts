plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    kotlin("kapt")
}

group = "app.harry.teddy"
version = "1.0"

kotlin {
    android()
    iosArm64 {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    js(IR) {
        useCommonJs()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(KotlinX.coroutines.core)
                api(Koin.core)
                api(Ktor.serializationKotlinx)
                api(Ktor.client.core)
                api(Ktor.client.contentNegotiation)
                api(Ktor.client.json)
                api(Ktor.client.serialization)
                api(Ktor.client.logging)
                api(Ktor.client.websockets)
                api(MultiplatformSettings.core)
                api(MultiplatformSettings.coroutines)
                api(MultiplatformSettings.serialization)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(AndroidX.lifecycle.viewModelKtx)
                api(Ktor.client.okHttp)
                api(MultiplatformSettings.datastore)
                api(AndroidX.dataStore.preferences)
                kapt(AndroidX.hilt.compiler)
                kapt(Google.dagger.hilt.compiler)
                implementation(Google.dagger.hilt.android)
            }
        }
        val iosArm64Main by getting
        val jsMain by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}