package com.nyandori.weatherforecastingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.gson.annotations.SerializedName
import com.nyandori.weatherforecastingapp.data.pollutionModels.Components
import com.nyandori.weatherforecastingapp.data.pollutionModels.Main
import com.nyandori.weatherforecastingapp.data.pollutionModels.Pollution
import com.nyandori.weatherforecastingapp.databinding.FragmentPollutionBinding

class PollutionFragment : AppCompatActivity() {
    private lateinit var binding: FragmentPollutionBinding
    companion object{

        var co: Double = 0.0

        var nh3: Double = 0.0

        var no: Double = 0.0

        var no2: Double = 0.0

        var o3: Double = 0.0

        var pm10: Double = 0.0

        var pm25: Double = 0.0

        var so2: Double = 0.0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
        binding = FragmentPollutionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pollutants = listOf(
            "co" to "CO",
            "nh3" to "NH3",
            "no" to "NO",
            "no2" to "NO2",
            "o3" to "O3",
            "pm10" to "PM10",
            "pm2_5" to "PM2.5",
            "so2" to "SO2"
        )
        val list = arrayListOf<BarEntry>()
        val txtBuilder = StringBuilder()
        val bundle = Bundle()
        bundle.putDouble("co",co)
        bundle.putDouble("nh3",nh3)
        bundle.putDouble("no",no)
        bundle.putDouble("no2",no2)
        bundle.putDouble("o3",o3)
        bundle.putDouble("pm10",pm10)
        bundle.putDouble("pm2_5",pm25)
        bundle.putDouble("so2",so2)

        pollutants.forEachIndexed { index, (key, label) ->
            val value = bundle.getDouble(key)
            if (value != null) {
                list.add(BarEntry((index + 1).toFloat(), value.toFloat()))
                txtBuilder.append("$label: $value\n")
            }
        }
        binding.textView.text = txtBuilder.toString()

        // Set up bar chart
        setupBarChart(binding.barChart, list, pollutants.map { it.second })

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupBarChart(barChart: BarChart, entries: ArrayList<BarEntry>, labels: List<String>) {
        // Create a dataset to hold the data
        val dataSet = BarDataSet(entries, "Pollutants")

        // Customize the appearance of the dataset
        dataSet.color = resources.getColor(R.color.blue) // Customize color as needed

        // Create a BarData object to hold the dataset
        val data = BarData(dataSet)

        // Assign the data to the chart
        barChart.data = data

        // Customize the appearance of the chart
        barChart.setDrawValueAboveBar(true)
        barChart.description.isEnabled = false
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)

        // Customize X-axis
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        val quarters = arrayOf("", "CO", "NH3", "NO", "NO2", "PM10", "O3", "PM2_5", "so2")
        val formatter : ValueFormatter = object : ValueFormatter(){
            override fun getAxisLabel(value : Float, axis: AxisBase): String {
                return quarters[value.toInt()]
            }
        }

        xAxis.valueFormatter = formatter
        // Invalidate the chart to refresh
        barChart.invalidate()
    }
}