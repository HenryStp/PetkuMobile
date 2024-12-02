plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.gms.google-services")
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

   

    implementation ("androidx.compose.material:material-icons-core:1.3.1")
    implementation ("androidx.compose.material:material-icons-extended:1.3.1")
    implementation ("androidx.compose.material:material:1.4.3")
    implementation(libs.firebase.firestore)
    implementation ("com.google.firebase:firebase-bom:32.1.0")
    implementation ("com.google.firebase:firebase-firestore:25.1.1")
    
    implementation(libs.firebase.auth)
    implementation ("com.google.firebase:firebase-auth:23.1.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    implementation ("androidx.compose.ui:ui:1.5.1") // Versi terbaru Compose
    implementation ("androidx.compose.material:material:1.5.1")
    implementation("androidx.compose.foundation:foundation:1.5.1")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.1")


    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    implementation ("androidx.navigation:navigation-compose:2.7.3")
    implementation ("androidx.appcompat:appcompat:1.6.1")// Use the latest version available

}