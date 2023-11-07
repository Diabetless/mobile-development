package com.CH2PS073.diabetless.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.CH2PS073.diabetless.R
import com.CH2PS073.diabetless.ui.welcome.WelcomePage

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, WelcomePage::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}