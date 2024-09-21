package com.nyandori.bettingapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.nyandori.bettingapp.databinding.ActivityBetBinding

class betActivity : AppCompatActivity(), View.OnClickListener {

   companion object{
       var betModelList: List<BetModel> = listOf()
   }

    var currentQuestionIndex = 0
    lateinit var binding: ActivityBetBinding
    lateinit var mAdView: AdView
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityBetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        binding.apply {
            btnNext.text = "Next"
            btnBack.setOnClickListener(this@betActivity)
            btnHome.setOnClickListener(this@betActivity)
            btnNext.setOnClickListener(this@betActivity)

        }

        loadQuestions()




    }

    private fun loadAd(){
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-1333228639990132/5843014655", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d(TAG, it) }
                mInterstitialAd = null


            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun loadQuestions(){

        if (currentQuestionIndex== betModelList.size){
            binding.btnNext.text = "Done"
            return
        }
        binding.apply {
            textLeagueHolder.text = betModelList[currentQuestionIndex].League
            textMatchHolder.text = betModelList[currentQuestionIndex].Match
            textTipHolder.text = betModelList[currentQuestionIndex].Tip
            textTimeHolder.text = betModelList[currentQuestionIndex].Time


        }

    }

    override fun onClick(view: View?) {
        val clickedBtn = view as Button
        if (clickedBtn.id==R.id.btn_next){
            loadAd()
            mInterstitialAd?.show(this)
            currentQuestionIndex ++
            loadQuestions()
        }
        else if(clickedBtn.id==R.id.btn_home){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            loadAd()
            mInterstitialAd?.show(this)
        } else if(clickedBtn.id==R.id.btn_back){

            if(currentQuestionIndex==0){
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                currentQuestionIndex --
                loadQuestions()
            }

        }

    }

}