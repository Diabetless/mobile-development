package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealCart

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
<<<<<<< HEAD
import com.ch2Ps073.diabetless.data.local.db.RoomRepository
import com.ch2Ps073.diabetless.data.remote.response.MealCart
import com.ch2Ps073.diabetless.databinding.ActivityMealCartBinding
import com.ch2Ps073.diabetless.ui.adapter.MealCartAdapter
=======
import com.CH2PS073.diabetless.ui.adapter.MealCartAdapter
import com.ch2Ps073.diabetless.data.local.db.RoomRepository
import com.ch2Ps073.diabetless.data.remote.response.MealCart
import com.ch2Ps073.diabetless.databinding.ActivityMealCartBinding
>>>>>>> chello
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail.MealDetailActivity

class MealCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealCartBinding
    private val mealCartAdapter = MealCartAdapter()

    private val mealCartViewModel by viewModels<MealCartViewModel> {
        MealCartViewModelFactory(
            RoomRepository(this.application)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setRecyclerView()
        getMealCart()

        setListeners()
    }

    private fun setRecyclerView() {
        with(binding.rvMealsCart) {
            adapter = mealCartAdapter
            layoutManager = LinearLayoutManager(this@MealCartActivity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.HORIZONTAL
                )
            )
        }
    }

    private fun getMealCart() {
        mealCartViewModel.getAllMealCart().observe(this) { mealList ->
            mealCartAdapter.setList(mealList as ArrayList<MealCart>)

            var gi = 0.0
            var gl = 0.0
            var cal = 0.0
            var carb = 0.0
            var proteins = 0.0
            var fats = 0.0

            for (meal in mealList) {
                gi += meal.glycemicIndex
                gl += meal.glycemicLoad
                cal += meal.calorie
                carb += meal.carbs
                proteins += meal.protein
                fats += meal.fats
            }

            val avgGi = gi/mealList.size
            val avgGl = gl/mealList.size

            binding.apply {
                tvGi.text = avgGi.toString()
                tvGl.text = avgGl.toString()
                tvCal.text = cal.toString()
                tvCarb.text = carb.toString()
                tvProteins.text = proteins.toString()
                tvFats.text = fats.toString()
            }

            showEmptyText(mealList.isEmpty())
        }
    }

    private fun showEmptyText(isEmpty: Boolean) {
        binding.apply {
            tvEmptyMeals.isVisible = isEmpty
            layoutContent.isVisible = !isEmpty
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

            mealCartAdapter.onItemClick = { id ->
                val iMealDetail = Intent(this@MealCartActivity, MealDetailActivity::class.java)
                iMealDetail.putExtra(MealDetailActivity.KEY_MEAL_ID, id)
                startActivity(iMealDetail)
            }

            mealCartAdapter.onDeleteClick = { id ->
                mealCartViewModel.deleteMealCart(id)
                getMealCart()
            }
        }
    }
}