package com.ch2Ps073.diabetless.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.ui.welcome.WelcomePage

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