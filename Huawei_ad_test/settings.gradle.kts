


pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://developer.huawei.com/repo/")  }
       
    }
    plugins {
        id("com.android.application") version "7.3.1" apply false
        id("com.android.library") version "7.3.1" apply false
        id("com.huawei.agconnect.agcp") version "1.7.3.302" apply false
        id("com.huawei.agconnect.apms") version "1.6.1.300" apply false
    }




}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://developer.huawei.com/repo/")  }
    }

}

rootProject.name = "Huawei_ad_test"
include(":app")
 