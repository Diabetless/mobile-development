package com.ch2Ps073.diabetless.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.ui.welcome.WelcomePage

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val icon: ImageView = findViewById(R.id.imageView2)
        icon.alpha = 0f
        icon.animate().setDuration(1500).alpha(1f).withEndAction {
            Handler().postDelayed({
                val intent = Intent(this, WelcomePage::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }, SPLASH_TIME_OUT)
        }
    }
}