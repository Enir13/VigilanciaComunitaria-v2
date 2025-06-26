plugins {
    id("com.google.gms.google-services") version "4.4.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.6.0")
        classpath("com.google.gms:google-services:4.4.0") // plugin google-services
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
    }
}
