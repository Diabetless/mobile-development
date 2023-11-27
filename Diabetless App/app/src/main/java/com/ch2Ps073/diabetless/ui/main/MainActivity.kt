package com.ch2Ps073.diabetless.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2Ps073.diabetless.data.remote.response.ArticleItem
import com.ch2Ps073.diabetless.databinding.ActivityMainBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.adapter.ArticlesAdapter
import com.ch2Ps073.diabetless.ui.detail.DetailActivity
import com.ch2Ps073.diabetless.ui.login.LoginActivity
import com.ch2Ps073.diabetless.ui.splashscreen.SplashScreen

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private val articlesAdapter = ArticlesAdapter()

    private val articleList = arrayListOf<ArticleItem>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, SplashScreen::class.java))
                finish()
            } else {
                val token = user.token
//                binding.textView.text = "ini token dari local storage $token"
                showToast(token)
            }
        }

        setupView()

//        binding.logoutButton.setOnClickListener {
//            viewModel.logout()
//        }

        setupToolbar()
        setRecyclerView()
        setSearchView()

        observeLoading()
        observeArticles()
        observeMessage()
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

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            val iLogin = Intent(this, LoginActivity::class.java)
            finishAffinity()
            startActivity(iLogin)
        }
    }


    private fun observeLoading() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }


    private fun observeArticles() {
        viewModel.allArticles.observe(this) {
            for (item in it) {
                if (!item.title!!.contains("MEALS - ")) {
                    articleList.add(item)
                }
            }
            articlesAdapter.setList(articleList)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun setSearchView() {
        with(binding.svArticle) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(keyword: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filter(newText)
                    return false
                }
            })
        }
    }

    private fun setRecyclerView() {
        articlesAdapter.onItemClick = { id ->
            val iDetail = Intent(this@MainActivity, DetailActivity::class.java)
            iDetail.putExtra(KEY_ID, id)
            startActivity(iDetail)
        }

        with(binding.rvArticles) {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.HORIZONTAL
                )
            )
        }
    }

    private fun observeMessage() {
        viewModel.toastMessage.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun filter(query: String) {
        val filteredlist = ArrayList<ArticleItem>()

        for (item in articleList) {
            if (item.title!!.toLowerCase().contains(query.toLowerCase())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            articlesAdapter.filterList(filteredlist)
        }
    }

    companion object {
        const val TOKEN_LOGIN: String = "user token"
        const val KEY_ID = "key_id"
    }
}