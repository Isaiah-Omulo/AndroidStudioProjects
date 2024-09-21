package com.nyandori.weatherforecastingapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.audiofx.BassBoost
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils.replace
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nyandori.weatherforecastingapp.adapter.RvAdapter
import com.nyandori.weatherforecastingapp.data.forecastModels.ForecastData
import com.nyandori.weatherforecastingapp.data.utils.RetrofitInstance
import com.nyandori.weatherforecastingapp.databinding.ActivityMainBinding
import com.nyandori.weatherforecastingapp.databinding.BottomSheetLayoutBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity :  AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var sheetLayoutBinding: BottomSheetLayoutBinding
    var criteria: Criteria? = null
    private lateinit var  locationManager: LocationManager
    private lateinit var dialog: BottomSheetDialog
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionId = 2
    private var searchText: String? = "New York"

    lateinit var pollutionFragment: PollutionFragment

    private lateinit var bestProvider: String
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        sheetLayoutBinding = BottomSheetLayoutBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
        dialog.setContentView(sheetLayoutBinding.root)
        setContentView(binding.root)

        pollutionFragment = PollutionFragment()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
      getLocation()


       binding.tvForecast.setOnClickListener {
           openDialog()
       }
binding.tvLocation.setOnClickListener {
    fetchLocation()
}
        binding.searchView.clearFocus()
        binding.searchView.setIconifiedByDefault(false)

        binding.searchView.setOnQueryTextListener(@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (query !== null){
                    searchText = query
                }

                getCurrentWeather(query)

                // This method is called when the user submits the query
                // You can perform any action you want with the submitted query here
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // This method is called whenever the text inside the search view changes
                // You can perform any action you want with the new text here
                // For example, you can filter a list based on the new text
                return false
            }
        })


    }

    private fun fetchLocation() {
        TODO("Not yet implemented")
    }


    private fun openDialog() {
        if (searchText.equals(null)){
            getForecast(searchText!!)
        }
        else{
            getForecast(searchText!!)
        }


        sheetLayoutBinding.rvForecast.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 1, RecyclerView.HORIZONTAL, false)
        }

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }
    private fun getForecast(cityName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getForecast(
                    cityName,
                    "metric",
                    applicationContext.getString(R.string.api_key)
                )
            } catch (e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@MainActivity, "Http error ${e.message}", Toast.LENGTH_SHORT).show()


                }
                Log.d("GetForecast", "IO error ${e.message}")

                return@launch
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@MainActivity, "Http error ${e.message}", Toast.LENGTH_SHORT).show()
                }
                Log.d("GetForecast", "HTTP error ${e.message}")
                return@launch

            }
            if (response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main) {
                    val data = response.body()!!

                    val forecastArray: ArrayList<ForecastData> = data.list as ArrayList<ForecastData>

                    val adapter = RvAdapter(forecastArray)
                    sheetLayoutBinding.rvForecast.adapter = adapter
                    sheetLayoutBinding.tvSheet.text = "Five days forecast in ${ data.city.name}"
                }
            }
        }

    }


    @SuppressLint("SetTextI18n")

    fun getCurrentWeather(cityName: String){
        GlobalScope.launch(Dispatchers.IO){
            val response = try {
              RetrofitInstance.api.getCurrentWeather(cityName, "metric", applicationContext.getString(R.string.api_key))
            } catch (e:IOException){

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@MainActivity, "Http error ${e.message}", Toast.LENGTH_SHORT).show()
                }
                return@launch
            } catch (e: Exception){
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@MainActivity, "Http error ${e.message}", Toast.LENGTH_SHORT).show()
                }
                return@launch

            }
            if (response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main){
                  val data = response.body()!!
                    val iconID = data.weather[0].icon
                    val imgUrl = "https://openweathermap.org/img/w/$iconID.png"
                    Picasso.get().load(imgUrl).into(binding.imgWeather)

                    binding.tvSunset.text =
                        dateFormatConverter(
                            data.sys.sunset.toLong()
                        )

                    binding.tvSunrise.text =
                        dateFormatConverter(
                            data.sys.sunrise.toLong()
                        )


                    binding.apply {
                        tvStatus.text = data.weather[0].description
                        tvWind.text = "${ data.wind.speed.toString() } KM/H"
                        tvLocation.text = "${data.name}\n${data.sys.country}"
                        tvTemp.text = "${data.main.temp.toInt()} °C"
                        tvFeelsLike.text = "Feels Like: ${data.main.feelsLike.toInt()}°C"
                        tvMinTemp.text = "Min Temp: ${data.main.tempMin.toInt()} °C"
                        tvMaxTemp.text= "Max Temp: ${data.main.tempMax.toInt()} °C"
                        tvHumidity.text = "${data.main.humidity} %"
                        tvPressure.text = "${data.main.pressure} hPa"
                        tvUpdateTime.text = "Last Update: ${SimpleDateFormat(
                            "hh:mm a",
                            Locale.ENGLISH
                        ).format(data.dt * 1000)}"

                        getPollution(data.coord.lat, data.coord.lon, cityName)
                    }

                }
            }
        }
    }

    private fun getPollution(lat:Double, lon:Double, cityName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getPollution(
                    lat,
                    lon,
                    "metric",
                    applicationContext.getString(R.string.api_key)
                )
            } catch (e: IOException) {

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@MainActivity, "Http error ${e.message}", Toast.LENGTH_SHORT)
                        .show()

                    Log.d("Get Pollution", "IO error ${e.message}")
                }
                return@launch
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@MainActivity, "Http error ${e.message}", Toast.LENGTH_SHORT)
                        .show()

                    Log.d("Get Pollution", "HTTP error ${e.message}")
                }
                return@launch

            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()!!
                    val num = data.list[0].main.aqi
                    binding.tvAirQual.text= when(num){
                        1->getString(R.string.good)
                        2->getString(R.string.fair)
                        3->getString(R.string.moderate)
                        4->getString(R.string.poor)
                        5->getString(R.string.very_poor)
                        else-> "No data"

                    }
                    binding.layoutPollution.setOnClickListener {


                        PollutionFragment.co = data.list[0].components.co
                        PollutionFragment.nh3 = data.list[0].components.nh3
                        PollutionFragment.no = data.list[0].components.no
                        PollutionFragment.no2 = data.list[0].components.no2
                        PollutionFragment.o3 = data.list[0].components.o3
                        PollutionFragment.pm10 = data.list[0].components.pm10
                        PollutionFragment.pm25 = data.list[0].components.pm25
                        PollutionFragment.so2 = data.list[0].components.so2

                        val intent = Intent(this@MainActivity, PollutionFragment::class.java)
                        startActivity(intent)


                    }

                }
            }

        }
    }

    private fun dateFormatConverter(date: Long): String {

        return SimpleDateFormat(
            "hh:mm a",
            Locale.ENGLISH
        ).format(Date(date * 1000))
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
               android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    private fun getLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101
            )
            return
        }
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                         searchText = list?.get(0)?.locality
                        getCurrentWeather( (list?.get(0)?.locality)!!)

                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }


    }

}

