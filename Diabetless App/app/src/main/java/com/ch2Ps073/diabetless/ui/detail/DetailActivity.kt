package com.ch2Ps073.diabetless.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.data.local.user.di.Injection
import com.ch2Ps073.diabetless.databinding.ActivityDetailBinding
import com.ch2Ps073.diabetless.ui.articles.ArticlesActivity.Companion.KEY_ID

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val id by lazy { intent.getStringExtra(KEY_ID) }
    val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(
            id!!,
            Injection.provideRepository(this)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun observeViewModel() {
        detailViewModel.apply {
            isLoading.observe(this@DetailActivity) {
                showLoading(it)
            }

            detailArticle.observe(this@DetailActivity) { detail ->
                if (detail != null) {
                    binding.apply {
                        Glide.with(binding.root)
                            .load(detail.imageUrl)
                            .into(binding.ivArticle)

                        val title = detail.title!!.replace("MEALS - ", "")

                        tvTitle.text = title
                        tvDateArticle.text = detail.postDate
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvDesc.text = Html.fromHtml(detail.content, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            tvDesc.text = detail.content
                        }
                    }
                }
            }

            toastMessage.observe(this@DetailActivity) { event ->
                event.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.layoutContent.isVisible = !isLoading
    }
}