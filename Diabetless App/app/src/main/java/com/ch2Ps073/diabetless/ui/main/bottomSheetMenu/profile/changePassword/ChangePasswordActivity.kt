package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.changePassword

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ch2Ps073.diabetless.databinding.ActivityChangePasswordBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<ChangePasswordViewModel> {
        ChangePasswordViewModelFactory.getInstance(this, lifecycleScope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupView()

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.saveChangeButton.setOnClickListener {
            val oldPW = binding.oldPasswordEditText.text.toString()
            val newPW = binding.newPasswordEditText.text.toString()
            val confirmPW = binding.confirmPasswordEditText.text.toString()

            if (newPW != confirmPW){
                showToast("your password doesnt match, please re check")
            } else {
                mainViewModel.getSession().observe(this){
                    viewModel.editPassword(it.token, oldPW, newPW)
                }
            }
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


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}