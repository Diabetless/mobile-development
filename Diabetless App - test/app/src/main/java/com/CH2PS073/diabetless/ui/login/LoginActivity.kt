package com.CH2PS073.diabetless.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.CH2PS073.diabetless.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
    }
}