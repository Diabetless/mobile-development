package com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ch2Ps073.diabetless.databinding.ActivityBloodSugarBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel

class BloodSugarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBloodSugarBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<BloodSugarViewModel> {
        BloodSugarViewModelFactory.getInstance(this, lifecycleScope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloodSugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val numbermm = binding.numberPickmm
        numbermm.minValue = 150
        numbermm.maxValue = 250

        val numberdL = binding.numberPickdL
        numberdL.minValue = 1
        numberdL.maxValue = 10


        binding.saveButton.setOnClickListener {
            val bloodslValue = numbermm.value/numberdL.value

            mainViewModel.getSession().observe(this){
                viewModel.setBloodSL(it.token, bloodslValue)
            }
        }
    }
}
