plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//    id("com.android.application")
//    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")// Untuk Firebase

}

android {
    namespace = "usu.adpl.petkumobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "usu.adpl.petkumobile"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation ("com.google.accompanist:accompanist-pager:0.25.1")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0") // Sesuaikan versi sesuai dengan versi stable
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.0")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation ("androidx.navigation:navigation-compose:2.7.3")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(platform("com.google.firebase:firebase-bom:32.2.0")) // Firebase BOM
    implementation("com.google.firebase:firebase-firestore-ktx") // Firestore KTX
//    implementation ("com.google.firebase:firebase-database:21.0.0")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx")
//    implementation ("com.github.bumptech.glide:glide:4.15.1")
//    implementation ("com.github.bumptech.glide:compose:1.0.0-alpha.1")
//    implementation ("com.google.accompanist:accompanist-glide:0.24.13-rc")
    // Or use the newer coil library if preferred
    implementation ("io.coil-kt:coil-compose:2.0.0")
    implementation ("androidx.activity:activity-ktx:1.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation ("androidx.compose.runtime:runtime:1.5.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.0") // Untuk LiveData & State
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.1")
    implementation("androidx.compose.runtime:runtime:1.5.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}