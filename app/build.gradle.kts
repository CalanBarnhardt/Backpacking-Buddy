plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.secrets.gradle)
    id("com.google.gms.google-services")
}

apply(plugin = "com.google.gms.google-services")

android {
    namespace = "pangolin.backpackingbuddy"
    compileSdk = 35

    defaultConfig {
        applicationId = "pangolin.backpackingbuddy"
        minSdk = 31
        targetSdk = 34
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

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
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
    implementation(libs.androidx.navigation)
    implementation(libs.kotlin.reflect)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.benchmark.traceprocessor.android)
    implementation(libs.google.firebase.auth.ktx)
    implementation(libs.google.play.services.location)
    implementation(libs.google.play.servies.maps)
    implementation(libs.google.maps.compose)
    implementation(libs.firebase.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //firebase
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.auth.ktx)
//    implementation(libs.firebase.firestore.ktx)
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation("com.google.firebase:firebase-analytics")


    //other stuff
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0") // Check for latest version
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0") // Check for latest version (often needed with viewModelScope)

    //icons
    implementation("androidx.compose.material:material-icons-core:<version>")
    implementation("androidx.compose.material:material-icons-extended:<version>")

}