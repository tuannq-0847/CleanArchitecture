import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.1")
    defaultConfig {
        applicationId = "com.karleinstein.basemvvm"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
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
    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("com.google.dagger:dagger:2.27")
    implementation("com.google.dagger:dagger-android-support:2.27")
    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
    kapt("com.google.dagger:dagger-compiler:2.26")
    kapt("com.google.dagger:dagger-android-processor:2.26")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
