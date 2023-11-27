package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.local.db.RoomRepository
import com.ch2Ps073.diabetless.data.local.user.di.Injection
import com.ch2Ps073.diabetless.data.remote.response.MealCart
import com.ch2Ps073.diabetless.databinding.ActivityMealDetailBinding
import com.ch2Ps073.diabetless.ui.articles.ArticlesActivity
import com.ch2Ps073.diabetless.ui.detail.DetailViewModel
import com.ch2Ps073.diabetless.ui.detail.DetailViewModelFactory
import java.lang.StringBuilder

class MealDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetailBinding

    private val id by lazy { intent.getStringExtra(ArticlesActivity.KEY_ID) }
    val mealDetailViewModel by viewModels<MealDetailViewModel> {
        MealDetailViewModelFactory(
            id!!,
            Injection.provideRepository(this),
            RoomRepository(this.application)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        observeViewModel()
        setListeners()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnExpandableLayout.setOnClickListener {
                val flagContentLayout =
                    if (layoutExpandableContent.isVisible) View.GONE else View.VISIBLE

                if (layoutExpandableContent.isVisible) {
                    iconExpandable.setImageDrawable(getDrawable(R.drawable.ic_next))
                } else {
                    iconExpandable.setImageDrawable(getDrawable(R.drawable.ic_down))
                }

                TransitionManager.beginDelayedTransition(layoutExpandable, AutoTransition())
                layoutExpandableContent.visibility = flagContentLayout
            }

            fabFavorite.setOnClickListener {
                if (mealDetailViewModel.isFavorited.value!!) {
                    mealDetailViewModel.deleteFavorite()
                    showToast("Meals deleted from Favorite!")
                } else {
                    if (mealDetailViewModel.detailMeal.value != null) {
                        mealDetailViewModel.insertFavorite(mealDetailViewModel.detailMeal.value!!)
                        showToast("Meals added to Favorite!")
                    }
                }
                mealDetailViewModel.checkFavorite()
            }

            fabMeals.setOnClickListener {
                if (mealDetailViewModel.isInCart.value!!) {
                    mealDetailViewModel.deleteMealCart()
                    showToast("Meals deleted from Cart Section!")
                } else {
                    if (mealDetailViewModel.detailMeal.value != null) {
                        val meal = MealCart(
                            id = mealDetailViewModel.detailMeal.value!!.id,
                            calorie = mealDetailViewModel.detailMeal.value!!.calorie,
                            carbs = mealDetailViewModel.detailMeal.value!!.carbs,
                            glycemicIndex = mealDetailViewModel.detailMeal.value!!.glycemicIndex,
                            glycemicLoad = mealDetailViewModel.detailMeal.value!!.glycemicLoad,
                            fats = mealDetailViewModel.detailMeal.value!!.fats,
                            protein = mealDetailViewModel.detailMeal.value!!.protein,
                            imageUrl = mealDetailViewModel.detailMeal.value!!.imageUrl,
                            postDate = mealDetailViewModel.detailMeal.value!!.postDate,
                            title = mealDetailViewModel.detailMeal.value!!.title,
                            content = mealDetailViewModel.detailMeal.value!!.content,
                        )

                        mealDetailViewModel.insertMealCart(meal)
                        showToast("Meals added to Cart Section!")
                    }
                }
                mealDetailViewModel.checkMealCart()
            }
        }
    }

    private fun observeViewModel() {
        mealDetailViewModel.apply {
            isLoading.observe(this@MealDetailActivity) {
                showLoading(it)
            }

            detailMeal.observe(this@MealDetailActivity) { detail ->
                if (detail != null) {
                    binding.apply {
                        Glide.with(binding.root)
                            .load(detail.imageUrl)
                            .into(binding.ivMeals)

                        tvTitle.text = detail.title

                        if (detail.glycemicIndex >= 70) {
                            tvGiFlag.text = StringBuilder("(High)")
                            pieGi.setColor("#eb4034")
                        } else if (detail.glycemicIndex > 55) {
                            tvGiFlag.text = StringBuilder("(Moderate)")
                            pieGi.setColor("#f3ff05")
                        } else {
                            tvGiFlag.text = StringBuilder("(Low)")
                            pieGi.setColor("#29ad1d")
                        }

                        if (detail.glycemicLoad >= 20) {
                            tvGlFlag.text = StringBuilder("(High)")
                            pieGl.setColor("#eb4034")
                        } else if (detail.glycemicLoad > 10) {
                            tvGlFlag.text = StringBuilder("(Moderate)")
                            pieGl.setColor("#f3ff05")
                        } else {
                            tvGlFlag.text = StringBuilder("(Low)")
                            pieGl.setColor("#29ad1d")
                        }

                        tvGi.text = detail.glycemicIndex.toString()
                        tvGl.text = detail.glycemicLoad.toString()

                        tvCal.text = detail.calorie.toString()
                        tvCarb.text = detail.carbs.toString()
                        tvProteins.text = detail.protein.toString()
                        tvFats.text = detail.fats.toString()

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvDesc.text = Html.fromHtml(detail.content, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            tvDesc.text = detail.content
                        }
                    }
                }
            }

            toastMessage.observe(this@MealDetailActivity) { event ->
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
        binding.layoutContainer.isVisible = !isLoading
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

    companion object {
        const val KEY_MEAL_ID = "key_id"
    }
}