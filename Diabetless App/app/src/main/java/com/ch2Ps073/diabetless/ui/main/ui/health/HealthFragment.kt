package com.ch2Ps073.diabetless.ui.main.ui.health

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.databinding.FragmentHealthBinding
import com.ch2Ps073.diabetless.ui.articles.ArticlesActivity
import com.ch2Ps073.diabetless.ui.main.MainActivity
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.BottomSheetMenuFragment
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.ProfileSettingActivity
import com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar.BloodSugarActivity
import com.ch2Ps073.diabetless.ui.main.ui.health.bodyComposition.BodyCompositionActivity
import com.ch2Ps073.diabetless.ui.main.ui.home.HomeFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class HealthFragment : Fragment() {

    private var _binding: FragmentHealthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mpLineChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val healthViewModel =
            ViewModelProvider(this).get(HealthViewModel::class.java)

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

        binding.bloodSugarButton.setOnClickListener {
            startActivity(Intent(requireContext(), BloodSugarActivity::class.java))
        }
        binding.updateBodyButton.setOnClickListener {
            startActivity(Intent(requireContext(), BodyCompositionActivity::class.java))
        }

        mpLineChart = binding.healthLineChart
        val lineDataSet1 = LineDataSet(dataValues1(), "Data Set 1")
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet1)

        val data = LineData(dataSets)
        mpLineChart.data = data
        mpLineChart.invalidate()


        return root
    }

    private fun dataValues1(): ArrayList<Entry> {
        val dataVals = ArrayList<Entry>()
        dataVals.add(Entry(0f, 20f))
        dataVals.add(Entry(1f, 24f))
        dataVals.add(Entry(2f, 2f))
        dataVals.add(Entry(3f, 10f))
        dataVals.add(Entry(4f, 28f))
        return dataVals
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
}