// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    kotlin("jvm").version("1.4.31")
}

buildscript {
    val hiltVersion = "2.33-beta"
//    val kotlinVersion by extra("1.4.31")
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-rc01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
//        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.5")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.0-rc")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}




allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://dl.bintray.com/tuannq-0847/BaseMVVM")
    }
}
