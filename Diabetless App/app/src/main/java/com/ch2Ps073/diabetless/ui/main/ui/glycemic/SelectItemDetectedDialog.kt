package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.BaseDetectedMeal
import com.ch2Ps073.diabetless.databinding.DialogSelectItemDetectedBinding
import com.ch2Ps073.diabetless.ui.adapter.BaseRecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class SelectItemDetectedDialog(
    private val context: Context,
    private val onSelectItem: (BaseDetectedMeal) -> Unit
) {
    private val binding: DialogSelectItemDetectedBinding = DialogSelectItemDetectedBinding.inflate(
        LayoutInflater.from(context)
    )

    private var dialog: BottomSheetDialog =
        BottomSheetDialog(context, R.style.SheetDialog).apply {
            setContentView(binding.root)
        }


    private val mealAdapter by lazy {
        object : BaseRecyclerViewAdapter<BaseDetectedMeal>(
            R.layout.item_meal_detected,
            bind = { item, holder, _, _ ->
                with(holder.itemView) {
                    if (item.imageBitmap != null) {
                        findViewById<ImageView>(R.id.img_food).setImageBitmap(item.imageBitmap)
                    }
                    findViewById<TextView>(R.id.tv_name).text = item.name

                    findViewById<View>(R.id.ly_meal_recommendation).setOnClickListener {
                        onSelectItem.invoke(item)
                    }
                }
            }
        ) {}
    }

    init {
        rvMeals()
    }

    private fun rvMeals() {
        binding.rvMeals.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mealAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMealItems(mealItems: List<BaseDetectedMeal>) {
        mealAdapter.submitList(mealItems)
        mealAdapter.notifyDataSetChanged()
    }

    fun showDialog() {
        dialog.show()
    }
}