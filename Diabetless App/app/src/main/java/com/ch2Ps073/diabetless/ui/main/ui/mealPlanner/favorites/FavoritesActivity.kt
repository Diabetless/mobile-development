package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.favorites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
<<<<<<< HEAD
import com.ch2Ps073.diabetless.data.local.db.RoomRepository
import com.ch2Ps073.diabetless.data.remote.response.Meal
import com.ch2Ps073.diabetless.databinding.ActivityFavoritesBinding
import com.ch2Ps073.diabetless.ui.adapter.MealsFavoriteAdapter
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail.MealDetailActivity
=======
import com.CH2PS073.diabetless.ui.adapter.MealsAdapter
import com.CH2PS073.diabetless.ui.adapter.MealsFavoriteAdapter
import com.ch2Ps073.diabetless.data.local.db.RoomRepository
import com.ch2Ps073.diabetless.data.remote.response.Meal
import com.ch2Ps073.diabetless.databinding.ActivityFavoritesBinding
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail.MealDetailActivity
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail.MealDetailViewModelFactory
>>>>>>> chello

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private val mealsAdapter = MealsFavoriteAdapter()

<<<<<<< HEAD
    private val favoritesViewModel by viewModels<FavoritesViewModel> {
=======
    val favoritesViewModel by viewModels<FavoritesViewModel> {
>>>>>>> chello
        FavoritesViewModelFactory(
            RoomRepository(this.application)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setRecyclerView()
        getFavoritesMeals()
        setListeners()
    }

    private fun setRecyclerView() {
        mealsAdapter.onItemClick = { id ->
            val iMealDetail = Intent(this, MealDetailActivity::class.java)
            iMealDetail.putExtra(MealDetailActivity.KEY_MEAL_ID, id)
            startActivity(iMealDetail)
        }

        with(binding.rvMeals) {
            adapter = mealsAdapter
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.HORIZONTAL
                )
            )
        }
    }

    private fun getFavoritesMeals() {
        favoritesViewModel.getAllFavorites().observe(this) {
            mealsAdapter.setList(it as ArrayList<Meal>)
            showEmptyText(it.isEmpty())
        }
    }

    private fun showEmptyText(isEmpty: Boolean) {
        binding.apply {
            tvEmptyFavorites.isVisible = isEmpty
            rvMeals.isVisible = !isEmpty
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

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
        }
    }
}