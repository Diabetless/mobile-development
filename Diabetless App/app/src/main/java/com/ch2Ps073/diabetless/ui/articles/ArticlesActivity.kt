package com.ch2Ps073.diabetless.ui.articles

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2Ps073.diabetless.data.remote.response.ArticleItem
import com.ch2Ps073.diabetless.ui.detail.DetailActivity
import com.ch2Ps073.diabetless.databinding.ActivityArticlesBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.adapter.ArticlesAdapter

class ArticlesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticlesBinding

    private val viewModel by viewModels<ArticlesViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val articlesAdapter = ArticlesAdapter()

    private val articleList = arrayListOf<ArticleItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

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
            finish()
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
            val iDetail = Intent(this@ArticlesActivity, DetailActivity::class.java)
            iDetail.putExtra(KEY_ID, id)
            startActivity(iDetail)
        }

        with(binding.rvArticles) {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(this@ArticlesActivity)
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