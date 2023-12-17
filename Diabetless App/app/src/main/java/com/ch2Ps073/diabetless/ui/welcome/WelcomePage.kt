package com.ch2Ps073.diabetless.ui.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ch2Ps073.diabetless.databinding.ActivityWelcomePageBinding
<<<<<<< HEAD
import com.ch2Ps073.diabetless.ui.login.LoginActivity
import com.ch2Ps073.diabetless.ui.register.SignupActivity
=======
import com.ch2Ps073.diabetless.ui.adapter.WelcomePagerAdapter
import com.ch2Ps073.diabetless.ui.login.LoginActivity
import com.ch2Ps073.diabetless.ui.register.SignupActivity
import com.google.android.material.tabs.TabLayoutMediator
>>>>>>> chello

class WelcomePage : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewPager()
        setupAction()
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
        supportActionBar?.hide()
    }

    private fun setupViewPager() {
        val sectionPagerAdapter = WelcomePagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.dotsIndicator.attachTo(binding.viewPager)
//        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
//            tab.text = resources.getString(tabTitles[position])
//        }.attach()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}