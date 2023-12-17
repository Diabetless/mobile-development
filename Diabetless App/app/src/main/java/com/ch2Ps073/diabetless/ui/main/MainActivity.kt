package com.ch2Ps073.diabetless.ui.main

<<<<<<< HEAD
=======
import android.Manifest
>>>>>>> chello
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
<<<<<<< HEAD
=======
import android.widget.Toast
>>>>>>> chello
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
<<<<<<< HEAD
import com.ch2Ps073.diabetless.ui.welcome.WelcomePage
=======
>>>>>>> chello
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
<<<<<<< HEAD

    private lateinit var binding: ActivityMainBinding

=======

    lateinit var binding: ActivityMainBinding

>>>>>>> chello
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
<<<<<<< HEAD
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_home, R.id.navigation_glycemic, R.id.navigation_meal_planner, R.id.navigation_health
=======

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
>>>>>>> chello
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
<<<<<<< HEAD
=======

    companion object {
        val REQUIRED_REQUIRED_PERMISSION = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        const val REQUEST_CODE_PERMISSIONS = 101
    }
>>>>>>> chello
}