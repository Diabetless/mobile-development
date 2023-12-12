package com.ch2Ps073.diabetless.ui.main.ui.health

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.BMIDataItem
import com.ch2Ps073.diabetless.data.remote.response.HealthUserResponse
import com.ch2Ps073.diabetless.databinding.FragmentHealthBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.BottomSheetMenuFragment
import com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar.BloodSugarActivity
import com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar.history.HistoryBloodActivity
import com.ch2Ps073.diabetless.ui.main.ui.health.bodyComposition.BodyCompositionActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlin.math.pow

class HealthFragment : Fragment() {

    private var _binding: FragmentHealthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mpLineChart: LineChart
    private lateinit var healthUserResponse: HealthUserResponse
    private lateinit var loadingProgressBar: ProgressBar

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val healthViewModel = ViewModelProvider(this)[HealthViewModel::class.java]

        _binding = FragmentHealthBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu -> {
                    BottomSheetMenuFragment().show(childFragmentManager, "bottomSheetMenu")
                    true
                }
                else -> false
            }
        }

        loadingProgressBar = binding.progressBar

        mainViewModel.getSession().observe(requireActivity()){
            healthViewModel.getHealthUser(it.token)

            showLoading(true)

            healthViewModel.healthUser.observe(viewLifecycleOwner) { response ->
                showLoading(false)
                healthUserResponse = response

                val lastBloodSugarLevel: Int? = healthUserResponse.bloodSugarData?.lastOrNull()?.level
                val bloodCardNumberTextView: TextView = binding.bloodCardNumber
                bloodCardNumberTextView.text = lastBloodSugarLevel?.toString() ?: "--"

                val lastHeight: Int? = healthUserResponse.bMIData?.lastOrNull()?.height
                val bodyCardTextNumberCMTextView: TextView = binding.bodyCardTextNumberCM
                bodyCardTextNumberCMTextView.text = lastHeight?.toString() ?: "..."

                val lastWeight: Int? = healthUserResponse.bMIData?.lastOrNull()?.weight
                val bodyCardTextNumberKGTextView: TextView = binding.bodyCardTextNumberKG
                bodyCardTextNumberKGTextView.text = lastWeight?.toString() ?: "--"

                setGraphValue(healthUserResponse.bMIData)
            }
        }

        binding.seeHistory.setOnClickListener {
            startActivity(Intent(requireContext(), HistoryBloodActivity::class.java))
        }

        binding.bloodSugarButton.setOnClickListener {
            startActivity(Intent(requireContext(), BloodSugarActivity::class.java))
        }

        binding.updateBodyButton.setOnClickListener {
            startActivity(Intent(requireContext(), BodyCompositionActivity::class.java))
        }

        return root
    }

    private fun setGraphValue(bmiList: List<BMIDataItem?>?) {
        val dataVals = ArrayList<Entry>()

        if (bmiList != null) {
            for ((index, bmi) in bmiList.withIndex()) {
                val m2: Float = bmi?.height!!.toFloat() / 100f
                val m2K: Float = m2.pow(2)
                val bmiV: Float = bmi.weight!!.toFloat() / m2K
                dataVals.add(Entry(index.toFloat(), bmiV))
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}