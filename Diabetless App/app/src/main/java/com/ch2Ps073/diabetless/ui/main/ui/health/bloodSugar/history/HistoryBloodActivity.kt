package com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar.history

import android.os.Bundle
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

        val healthViewModel = ViewModelProvider(this)[HealthViewModel::class.java]

        mainViewModel.getSession().observe(this){
            healthViewModel.getHealthUser(it.token)
            healthViewModel.healthUser.observe(this) { response ->
                healthUserResponse = response

                setGraphValue(healthUserResponse.bloodSugarData)
            }
        }
    }

    private fun setGraphValue(bmiList: List<BloodSugarDataItem?>?) {
        val dataVals = ArrayList<Entry>()

        if (bmiList != null) {
            for ((index, bmi) in bmiList.withIndex()) {
                if (bmi != null) {
                    dataVals.add(Entry(index.toFloat(), bmi.level!!.toFloat()))
                }
            }
        }

        mpLineChart = binding.healthLineChart

        val lineDataSet = LineDataSet(dataVals, "BMI Data")
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)
        val data = LineData(dataSets)
        mpLineChart.data = data
        mpLineChart.invalidate()
    }
}