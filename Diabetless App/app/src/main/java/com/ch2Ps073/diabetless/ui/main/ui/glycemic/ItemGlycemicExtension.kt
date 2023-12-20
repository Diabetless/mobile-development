package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.BaseDetectedMeal
import com.ch2Ps073.diabetless.data.remote.response.DetectedMealItem
import com.ch2Ps073.diabetless.data.remote.response.RecommendationsMeal
import com.ch2Ps073.diabetless.databinding.ItemGlycemicIndexBinding
import com.ch2Ps073.diabetless.ui.adapter.BaseRecyclerViewAdapter
import com.ch2Ps073.diabetless.utils.setImageFromUrl


fun recommendationAdapter(onClickRecommendationsMeal: ((RecommendationsMeal) -> Unit)? = null) =
    object : BaseRecyclerViewAdapter<RecommendationsMeal>(
        R.layout.item_meal_detected,
        bind = { item, holder, _, _ ->
            with(holder.itemView) {
                findViewById<ImageView>(R.id.img_food).setImageFromUrl(item.imageUrl ?: "")
                findViewById<TextView>(R.id.tv_name).text = item.name

                findViewById<View>(R.id.ly_meal_recommendation).setOnClickListener {
                    onClickRecommendationsMeal?.invoke(item)
                }
            }
        }
    ) {}

fun ItemGlycemicIndexBinding.setupRvRecommendation(onClickRecommendationsMeal: ((RecommendationsMeal) -> Unit)? = null) = rvRecommendation.apply {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    adapter = recommendationAdapter(onClickRecommendationsMeal)
}

@SuppressLint("SetTextI18n", "NotifyDataSetChanged")
fun ItemGlycemicIndexBinding.setComponents(
    context: Context,
    detectedMeal: BaseDetectedMeal,
    imageCaptureBitmap: Bitmap? = null,
) {
    tvDetected.text =
        (detectedMeal.name ?: "").replaceFirstChar { it.uppercase() }
    tvGi.text = (detectedMeal.nutritionFact?.gi ?: "").toString()
    tvGl.text = (detectedMeal.nutritionFact?.gl ?: "").toString()
    tvGiFlag.text =
        (detectedMeal.nutritionFact?.giLevel ?: "").replaceFirstChar { it.uppercase() }
    tvGlFlag.text =
        (detectedMeal.nutritionFact?.glLevel ?: "").replaceFirstChar { it.uppercase() }
    tvFact.text =
        "Nutrition Facts (${(detectedMeal.serving ?: "")})"
    tvCal.text =
        (detectedMeal.nutritionFact?.calories ?: "").toString()
    tvCarb.text =
        (detectedMeal.nutritionFact?.carbohydrates ?: "").toString()
    tvProteins.text =
        (detectedMeal.nutritionFact?.proteins ?: "").toString()
    tvFats.text = (detectedMeal.nutritionFact?.fats ?: "").toString()


    when (val background: Drawable = lyGiLevel.background) {
        is GradientDrawable -> {
            val color = ContextCompat.getColor(
                context,
                if (detectedMeal.nutritionFact!!.giLevel!!.equals(
                        "low",
                        ignoreCase = true
                    )
                ) R.color.green
                else if (detectedMeal.nutritionFact!!.giLevel!!.equals(
                        "moderate",
                        ignoreCase = true
                    )
                )
                    R.color.yellow
                else R.color.red
            )

            background.setStroke(30, color)
        }
    }

    when (val background: Drawable = lyGlLevel.background) {
        is GradientDrawable -> {
            val color = ContextCompat.getColor(
                context,
                if (detectedMeal.nutritionFact!!.glLevel!!.equals(
                        "low",
                        ignoreCase = true
                    )
                ) R.color.green
                else if (detectedMeal.nutritionFact!!.glLevel!!.equals(
                        "moderate",
                        ignoreCase = true
                    )
                )
                    R.color.yellow
                else R.color.red
            )

            background.setStroke(30, color)
        }
    }

    lyMealRecommendation.visibility =
        if (detectedMeal is DetectedMealItem) View.VISIBLE else View.GONE

    if (detectedMeal is DetectedMealItem) {
        imgCapture.setImageBitmap(imageCaptureBitmap)
        rvRecommendation.visibility =
            if (detectedMeal.recommendations.isNullOrEmpty()) View.GONE else View.VISIBLE

        tvNoItemRecommendation.visibility =
            if (detectedMeal.recommendations.isNullOrEmpty()) View.VISIBLE else View.GONE

        if (!detectedMeal.recommendations.isNullOrEmpty()) {
            val adapter = rvRecommendation.adapter as BaseRecyclerViewAdapter<RecommendationsMeal>
            adapter.submitList(detectedMeal.recommendations)
            adapter.notifyDataSetChanged()

            /*recommendationAdapter().submitList(detectedMeal.recommendations)
            recommendationAdapter().notifyDataSetChanged()*/
        }
    } else if (detectedMeal is RecommendationsMeal) {
        imgCapture.setImageFromUrl(detectedMeal.imageUrl ?: "")
    }
}
