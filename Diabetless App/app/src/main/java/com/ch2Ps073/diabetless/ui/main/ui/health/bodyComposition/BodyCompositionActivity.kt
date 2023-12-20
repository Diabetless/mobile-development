package com.ch2Ps073.diabetless.ui.main.ui.health.bodyComposition

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ch2Ps073.diabetless.databinding.ActivityBodyCompositionBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel

class BodyCompositionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBodyCompositionBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<BodyCompositionViewModel> {
        BodyCompositionViewModelFactory.getInstance(this, lifecycleScope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyCompositionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val numbercm = binding.numberPickcm
        numbercm.minValue = 110
        numbercm.maxValue = 220

        val numberkg = binding.numberPickkg
        numberkg.minValue = 15
        numberkg.maxValue = 200


        binding.saveButton.setOnClickListener {
            mainViewModel.getSession().observe(this){
                viewModel.setBody(it.token, numbercm.value, numberkg.value)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }
}