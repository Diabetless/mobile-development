package com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
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
        setupView()

        val numbermm = binding.numberPickmm
        numbermm.minValue = 1
        numbermm.maxValue = 1000

        binding.saveButton.setOnClickListener {
            mainViewModel.getSession().observe(this){
                viewModel.setBloodSL(it.token, numbermm.value)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
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
