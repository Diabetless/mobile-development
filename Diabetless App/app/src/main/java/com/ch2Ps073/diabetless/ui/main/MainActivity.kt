package com.ch2Ps073.diabetless.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.databinding.ActivityMainBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.splashscreen.SplashScreen
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

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
                //changeCamMenu()

                navController = findNavController(R.id.nav_host_fragment_activity_main)

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
                        R.id.navigation_meal_planner,
                        R.id.navigation_glycemic,
                        R.id.navigation_health,
                        R.id.navigation_profile
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.camera.lyCustomCameraNav.visibility =
                if (destination.id == R.id.navigation_glycemic) View.GONE else View.VISIBLE

            if (destination.id == R.id.navigation_glycemic) {

            }
        }

        binding.camera.btnCamera.setOnClickListener {
            navController.navigate(R.id.navigation_glycemic)
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
        val REQUIRED_REQUIRED_PERMISSION = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        private const val REQUEST_CODE_PERMISSIONS = 101
    }

    @SuppressLint("RestrictedApi")
    private fun changeCamMenu() {
        val navView = binding.navView
        val bottomMenuView = navView.getChildAt(0) as BottomNavigationMenuView
        val view = bottomMenuView.getChildAt(2)
        val itemView = (view as BottomNavigationItemView).apply {

        }

        val viewCustom =
            LayoutInflater.from(this).inflate(R.layout.custom_camera_nav, bottomMenuView, false)
        itemView.addView(viewCustom)
    }
}