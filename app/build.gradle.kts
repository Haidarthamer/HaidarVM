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
        versionCode = 2
        versionName = "0.2.0"
    }
}
dependencies {
    implementation("org.apache.commons:commons-compress:1.28.0")
    implementation("androidx.core:core-ktx:1.17.0")
}