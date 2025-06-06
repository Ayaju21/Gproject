plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.worksync"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.worksync"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.google.android.material:material:1.11.0")
    implementation("com.wdullaer:materialdatetimepicker:4.2.3")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.android.volley:volley:1.2.0")
}
