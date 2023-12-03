package com.ch2Ps073.diabetless.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.databinding.ActivityMainBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.ui.glycemic.GlycemicIndexFragment
import com.ch2Ps073.diabetless.ui.splashscreen.SplashScreen
import com.ch2Ps073.diabetless.ui.welcome.WelcomePage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, SplashScreen::class.java))
                finish()
            } else {
                setContentView(binding.root)

                checkImagePermission()

                val navView: BottomNavigationView = binding.navView

                val navController = findNavController(R.id.nav_host_fragment_activity_main)

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    if (destination.id == R.id.navigation_glycemic) {
                        navView.visibility = View.GONE
                    } else {
                        navView.visibility = View.VISIBLE
                    }
                }

                navView.setOnNavigationItemSelectedListener { item ->
                    if (item.itemId == R.id.navigation_glycemic) {
                        Toast.makeText(this, "HALO", Toast.LENGTH_SHORT).show()
                    }

                    true
                }

                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_home,
                        R.id.navigation_glycemic,
                        R.id.navigation_meal_planner,
                        R.id.navigation_health
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

    private fun checkImagePermission() = REQUIRED_CAMERA_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 101
    }
}