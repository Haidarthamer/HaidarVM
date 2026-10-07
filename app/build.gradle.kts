plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.haidarthamer.haidarvm"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.haidarthamer.haidarvm"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }
}

dependencies {
    implementation("org.apache.commons:commons-compress:1.28.0")
}
