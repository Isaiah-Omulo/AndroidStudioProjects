plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")





}
// Conditionally apply plugins
val shouldApplyAndroidApplication = true // Example condition
val shouldApplyAndroidLibrary = true // Example condition
val shouldApplyAgcp = true // Example condition
val shouldApplyApms = true // Example condition

if (shouldApplyAndroidApplication) {
    plugins.apply("com.android.application")
}
if (shouldApplyAndroidLibrary) {
    plugins.apply("com.android.library")
}
if (shouldApplyAgcp) {
    plugins.apply("com.huawei.agconnect.agcp")
}
if (shouldApplyApms) {
    plugins.apply("com.huawei.agconnect.apms")
}




android {
    namespace = "com.nyandori.huawei_ad_test"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nyandori.huawei_ad_test"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures{
        viewBinding = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("com.huawei.hms:ads-prime:3.4.69.302")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}