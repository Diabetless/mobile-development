package com.ch2Ps073.diabetless.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.databinding.ActivityMainBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.splashscreen.SplashScreen
import com.ch2Ps073.diabetless.ui.welcome.WelcomePage
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, SplashScreen::class.java))
                finish()
            } else {
                setContentView(binding.root)

                val navView: BottomNavigationView = binding.navView

                val navController = findNavController(R.id.nav_host_fragment_activity_main)
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_home, R.id.navigation_glycemic, R.id.navigation_meal_planner, R.id.navigation_health
                    )
                )
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)

                setupView()
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
}