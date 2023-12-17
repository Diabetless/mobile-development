<<<<<<< HEAD
package com.ch2Ps073.diabetless.ui.adapter
=======
package com.CH2PS073.diabetless.ui.adapter
>>>>>>> chello

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.data.remote.response.Meal
<<<<<<< HEAD
=======
import com.ch2Ps073.diabetless.data.remote.response.MealItem
import com.ch2Ps073.diabetless.databinding.ItemArticlesRowBinding
>>>>>>> chello
import com.ch2Ps073.diabetless.databinding.ItemMealsRowBinding

class MealsFavoriteAdapter : RecyclerView.Adapter<MealsFavoriteAdapter.ViewHolder>() {
    private val mealItems: ArrayList<Meal> = arrayListOf()
    var onItemClick: ((String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mealList: ArrayList<Meal>) {
        this.mealItems.clear()
        this.mealItems.addAll(mealList)
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
        fun bind(meal: Meal) {
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