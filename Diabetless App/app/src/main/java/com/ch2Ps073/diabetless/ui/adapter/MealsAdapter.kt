package com.CH2PS073.diabetless.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.data.remote.response.Meal
import com.ch2Ps073.diabetless.data.remote.response.MealItem
import com.ch2Ps073.diabetless.databinding.ItemArticlesRowBinding
import com.ch2Ps073.diabetless.databinding.ItemMealsRowBinding

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.ViewHolder>() {
    private val mealItems: ArrayList<MealItem> = arrayListOf()
    var onItemClick: ((String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mealList: ArrayList<MealItem>) {
        this.mealItems.clear()
        this.mealItems.addAll(mealList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterlist: ArrayList<MealItem>) {
        this.mealItems.clear()
        mealItems.addAll(filterlist)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMealsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mealItems[position])
    }

    override fun getItemCount() = mealItems.size

    inner class ViewHolder(private var binding: ItemMealsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: MealItem) {
            with(binding) {
                Glide.with(binding.root)
                    .load(meal.imageUrl)
                    .into(binding.ivMeals)

                tvTitleMeals.text = meal.title
                tvGi.text = StringBuilder("${meal.glycemicIndex} GI")
                tvGl.text = StringBuilder("${meal.glycemicLoad} GL")
                tvCal.text = StringBuilder("${meal.calorie} kcal")

                root.setOnClickListener {
                    onItemClick?.invoke(meal.id!!)
                }
            }
        }
    }
}