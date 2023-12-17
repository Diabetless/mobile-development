package com.ch2Ps073.diabetless.ui.splashscreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
import com.ch2Ps073.diabetless.R
=======
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainActivity
import com.ch2Ps073.diabetless.ui.main.MainViewModel
>>>>>>> chello
import com.ch2Ps073.diabetless.ui.welcome.WelcomePage

class SplashScreen : AppCompatActivity() {

    private val viewModel by viewModels<SplashScreenViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val SPLASH_TIME_OUT: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setupView()
        val icon: ImageView = findViewById(R.id.imageView2)
        icon.alpha = 0f
        icon.animate().setDuration(1500).alpha(1f).withEndAction {
            Handler().postDelayed({

                viewModel.getSession().observe(this@SplashScreen) { user ->
                    if (!user.isLogin) {
                        val intent = Intent(this, WelcomePage::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                }
            }, SPLASH_TIME_OUT)
        }

        setupView()
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
<<<<<<< HEAD
=======
        supportActionBar?.hide()
>>>>>>> chello
    }
}