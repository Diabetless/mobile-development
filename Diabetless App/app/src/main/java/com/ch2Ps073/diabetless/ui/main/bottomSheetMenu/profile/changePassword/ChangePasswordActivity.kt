package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.changePassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ch2Ps073.diabetless.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }
}