package com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar.history

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.data.remote.response.BloodSugarDataItem
import com.ch2Ps073.diabetless.data.remote.response.HealthUserResponse
import com.ch2Ps073.diabetless.databinding.ActivityHistoryBloodBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel
import com.ch2Ps073.diabetless.ui.main.ui.health.HealthViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class HistoryBloodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBloodBinding
    private lateinit var mpLineChart: LineChart
    private lateinit var healthUserResponse: HealthUserResponse

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBloodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupView()

        val healthViewModel = ViewModelProvider(this)[HealthViewModel::class.java]

        mainViewModel.getSession().observe(this){
            healthViewModel.getHealthUser(it.token)
            healthViewModel.healthUser.observe(this) { response ->
                healthUserResponse = response

                setGraphValue(healthUserResponse.bloodSugarData)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setGraphValue(bmiList: List<BloodSugarDataItem?>?) {
        val dataVals = ArrayList<Entry>()
        var totalBmi = 0.0f
        var minBmi = Float.MAX_VALUE
        var maxBmi = Float.MIN_VALUE

        if (bmiList != null) {
            for ((index, bmi) in bmiList.withIndex()) {
                if (bmi != null) {
                    dataVals.add(Entry(index.toFloat(), bmi.level!!.toFloat()))

                    minBmi = minOf(minBmi, bmi.level.toFloat())
                    maxBmi = maxOf(maxBmi, bmi.level.toFloat())

                    totalBmi += bmi.level.toFloat()
                }
            }

            val averageBmi = totalBmi / bmiList.size

            val averageTextView: TextView = binding.avgChartNumber
            val minTextView: TextView = binding.minChartNumber
            val maxTextView: TextView = binding.maxChartNumber

            averageTextView.text = String.format("%.2f", averageBmi)
            minTextView.text = String.format("%.2f", minBmi)
            maxTextView.text = String.format("%.2f", maxBmi)
        }

        mpLineChart = binding.healthLineChart
        mpLineChart.description = null

        val lineDataSet = LineDataSet(dataVals, "BMI Data")
        lineDataSet.color = 0xFFE4C0BE.toInt()
        lineDataSet.setCircleColor(0xFFE4C0BE.toInt())

        val xAxis = mpLineChart.xAxis
        val yAxisLeft = mpLineChart.axisLeft
        val yAxis = mpLineChart.axisRight

        xAxis.setDrawGridLines(false)
        yAxis.setDrawGridLines(false)
        yAxisLeft.setDrawGridLines(false)

        yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawAxisLine(false)

        val limit1 = 70f
        val limit2 = 140f

        val limitLine1 = LimitLine(limit1, "")
        limitLine1.lineWidth = 1f
        limitLine1.lineColor = 0xFF00FF00.toInt() // Green color
        limitLine1.lineWidth = 2f
        yAxis.addLimitLine(limitLine1)

        val limitLine2 = LimitLine(limit2, "")
        limitLine2.lineWidth = 1f
        limitLine2.lineColor = 0xFFFFFF00.toInt() // Yellow color
        limitLine2.lineWidth = 2f
        yAxis.addLimitLine(limitLine2)

        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val legend = mpLineChart.legend
        legend.isEnabled = true
        legend.form = Legend.LegendForm.LINE
        legend.textColor = Color.BLACK

        val legendEntries = ArrayList<LegendEntry>()
        legendEntries.add(LegendEntry("Batas bawah", Legend.LegendForm.LINE, 10f, 2f, null, 0xFF00FF00.toInt()))
        legendEntries.add(LegendEntry("Batas atas", Legend.LegendForm.LINE, 10f, 2f, null, 0xFFFFFF00.toInt()))

        legend.setCustom(legendEntries)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)

        val data = LineData(dataSets)

        mpLineChart.data = data
        mpLineChart.invalidate()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}