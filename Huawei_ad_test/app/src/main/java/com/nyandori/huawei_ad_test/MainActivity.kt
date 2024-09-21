package com.nyandori.huawei_ad_test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import com.huawei.hms.ads.HwAds
import com.huawei.hms.ads.banner.BannerView
import com.nyandori.huawei_ad_test.databinding.ActivityMainBinding
import com.nyandori.huawei_ad_test.ui.theme.Huawei_ad_testTheme

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var bannerView: BannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        HwAds.init(this)

        bannerView = findViewById(R.id.hw_banner_view)
        // Set the ad unit ID and ad dimensions. "testw6vs28auh3" is a dedicated test ad unit ID.
        if (bannerView != null) {
            bannerView.adId = "testw6vs28auh3"
        }
        if (bannerView != null) {
            bannerView.bannerAdSize = BannerAdSize.BANNER_SIZE_360_57
        }
        // Set the refresh interval to 60 seconds.
        bannerView.setBannerRefresh(60)
        // Create an ad request to load an ad.
        val adParam = AdParam.Builder().build()
        bannerView.loadAd(adParam)

      bannerView.adListener = adListener

    }

    private val adListener: AdListener = object : AdListener() {
        override fun onAdLoaded() {
            // Called when an ad is loaded successfully.
            Toast.makeText(this@MainActivity, "Loaded successfully", Toast.LENGTH_SHORT).show()

        }
        override fun onAdFailed(errorCode: Int) {
            // Called when an ad fails to be loaded.
            Toast.makeText(this@MainActivity, "Could not load the ad ${errorCode}", Toast.LENGTH_SHORT).show()
        }
        override fun onAdOpened() {
            // Called when an ad is opened.

        }
        override fun onAdClicked() {
            // Called when an ad is clicked.

        }
        override fun onAdLeave() {
            // Called when an ad leaves an app.

        }
        override fun onAdClosed() {
            // Called when an ad is closed.

        }
    }
}
