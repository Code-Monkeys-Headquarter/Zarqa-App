plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "code.monkeys.zarqa"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "code.monkeys.zarqa"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val lottieVersion = "6.2.0"
    //noinspection UseTomlInstead
    implementation("com.airbnb.android:lottie:$lottieVersion")

    val glideVersion = "4.16.0"
    //noinspection UseTomlInstead
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    val chipNavigationVersion = "1.4.0"
    //noinspection UseTomlInstead
    implementation("com.github.ismaeldivita:chip-navigation-bar:$chipNavigationVersion")

    val retrofitVersion = "2.11.0"
    val loggingInterceptorVersion = "4.9.0"
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    //noinspection GradleDependency,UseTomlInstead
    implementation("com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion")
    //noinspection UseTomlInstead
    implementation("com.squareup.okio:okio:3.2.0")

    val recyclerViewVersion = "1.3.2"
    //noinspection UseTomlInstead
    implementation("androidx.recyclerview:recyclerview:$recyclerViewVersion")

    val lifecycleVersion = "2.7.0"
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    val roomVersion = "2.6.1"
    //noinspection UseTomlInstead
    implementation("androidx.room:room-runtime:$roomVersion")
    //noinspection UseTomlInstead
    implementation("androidx.room:room-ktx:$roomVersion")
    //noinspection UseTomlInstead
    ksp("androidx.room:room-compiler:$roomVersion")

    val coroutinesVersion = "1.8.0"
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Circle Image dari Hodenhof
    //noinspection UseTomlInstead
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Motion Layout
    //noinspection UseTomlInstead
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")

    // MySql Connector
    //noinspection UseTomlInstead
    implementation("mysql:mysql-connector-java:8.0.26")

    // Tab layout
    implementation(libs.androidx.viewpager2)

    // Data Store
    implementation (libs.androidx.datastore.preferences)

    // Camera X
    val cameraXversion = "1.2.2"
    implementation("androidx.camera:camera-core:$cameraXversion")
    implementation("androidx.camera:camera-camera2:$cameraXversion")
    implementation("androidx.camera:camera-lifecycle:$cameraXversion")
    implementation("androidx.camera:camera-video:$cameraXversion")
    implementation("androidx.camera:camera-view:$cameraXversion")
    implementation("androidx.camera:camera-extensions:$cameraXversion")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")

    // All:
    implementation ("com.cloudinary:cloudinary-android:2.5.0")

// Download + Preprocess:
    implementation ("com.cloudinary:cloudinary-android-download:2.5.0")
    implementation ("com.cloudinary:cloudinary-android-preprocess:2.5.0")

//    Shimmer Loading by Facebook
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

//    Swipe Refresh Layout
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.annotation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}