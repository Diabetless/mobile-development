package com.CH2PS073.diabetless.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.CH2PS073.diabetless.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()
    }
}