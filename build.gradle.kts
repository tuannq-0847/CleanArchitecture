// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    kotlin("jvm").version("1.3.72")
}

buildscript {
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.5")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:0.10.1")
    }
}




allprojects {
    repositories {
        google()
        jcenter()
    }
}
