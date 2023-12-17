package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.BaseDetectedMeal
import com.ch2Ps073.diabetless.data.remote.response.DetectedMealItem
import com.ch2Ps073.diabetless.data.remote.response.RecommendationsMeal
import com.ch2Ps073.diabetless.databinding.BottomSheetGlycemixCameraLayoutBinding
import com.ch2Ps073.diabetless.ui.adapter.BaseRecyclerViewAdapter
import com.ch2Ps073.diabetless.utils.setImageFromUrl
import com.google.android.material.bottomsheet.BottomSheetDialog


class MealsDetailsDialog(
    private val context: Context,
    private val onDismiss: (DialogInterface) -> Unit,
    private val onClickRecommendationsMeal: ((RecommendationsMeal) -> Unit)? = null,
) {

    private var binding: BottomSheetGlycemixCameraLayoutBinding =
        BottomSheetGlycemixCameraLayoutBinding.inflate(LayoutInflater.from(context)).apply {
            lyResult.visibility = View.VISIBLE
            lyNoResult.visibility = View.GONE
        }

    private var glycemixBottomMenu: BottomSheetDialog =
        BottomSheetDialog(context, R.style.SheetDialog).apply {
            setContentView(binding.root)
            setOnDismissListener { d ->
                onDismiss.invoke(d)
            }
        }

    private val recommendationAdapter by lazy {
        object : BaseRecyclerViewAdapter<RecommendationsMeal>(
            R.layout.item_recommendation,
            bind = { item, holder, _, _ ->
                with(holder.itemView) {
                    findViewById<ImageView>(R.id.img_food).setImageFromUrl(item.imageUrl ?: "")
                    findViewById<TextView>(R.id.tv_name).text = item.name

                    findViewById<View>(R.id.ly_recommendation).setOnClickListener {
                        onClickRecommendationsMeal?.invoke(item)

                        //onClickRecommendationItem(item)
                    }
                }
            }
        ) {}
    }

    init {
        rvCommendation()
    }

    private fun rvCommendation() {
        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationAdapter
        }
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun componentsSetup(detectedMealItem: BaseDetectedMeal?, bitmap: Bitmap? = null) {
        if (detectedMealItem == null) {
            binding.lyResult.visibility = View.GONE
            binding.lyNoResult.visibility = View.VISIBLE

            binding.imgCaptureNoResult.setImageBitmap(bitmap)
        } else {
            binding.lyResult.visibility = View.VISIBLE
            binding.lyNoResult.visibility = View.GONE

            binding.tvDetected.text =
                (detectedMealItem.name ?: "").replaceFirstChar { it.uppercase() }
            binding.tvGi.text = (detectedMealItem.nutritionFact?.gi ?: "").toString()
            binding.tvGl.text = (detectedMealItem.nutritionFact?.gl ?: "").toString()
            binding.tvGiFlag.text =
                (detectedMealItem.nutritionFact?.giLevel ?: "").replaceFirstChar { it.uppercase() }
            binding.tvGlFlag.text =
                (detectedMealItem.nutritionFact?.glLevel ?: "").replaceFirstChar { it.uppercase() }
            binding.tvFact.text =
                "Nutrition Facts (${(detectedMealItem.serving ?: "")})"
            binding.tvCal.text =
                (detectedMealItem.nutritionFact?.calories ?: "").toString()
            binding.tvCarb.text =
                (detectedMealItem.nutritionFact?.carbohydrates ?: "").toString()
            binding.tvProteins.text =
                (detectedMealItem.nutritionFact?.proteins ?: "").toString()
            binding.tvFats.text = (detectedMealItem.nutritionFact?.fats ?: "").toString()


            when (val background: Drawable = binding.lyGiLevel.background) {
                is GradientDrawable -> {
                    val color = ContextCompat.getColor(
                        context,
                        if (detectedMealItem.nutritionFact!!.giLevel!!.equals(
                                "low",
                                ignoreCase = true
                            )
                        ) R.color.green
                        else if (detectedMealItem.nutritionFact!!.giLevel!!.equals(
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

            when (val background: Drawable = binding.lyGlLevel.background) {
                is GradientDrawable -> {
                    val color = ContextCompat.getColor(
                        context,
                        if (detectedMealItem.nutritionFact!!.glLevel!!.equals(
                                "low",
                                ignoreCase = true
                            )
                        ) R.color.green
                        else if (detectedMealItem.nutritionFact!!.glLevel!!.equals(
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

            binding.lyRecommendation.visibility =
                if (detectedMealItem is DetectedMealItem) View.VISIBLE else View.GONE

            if (detectedMealItem is DetectedMealItem) {
                binding.imgCapture.setImageBitmap(bitmap)
                binding.rvRecommendation.visibility =
                    if (detectedMealItem.recommendations.isNullOrEmpty()) View.GONE else View.VISIBLE

                binding.tvNoItemRecommendation.visibility =
                    if (detectedMealItem.recommendations.isNullOrEmpty()) View.VISIBLE else View.GONE

                if (!detectedMealItem.recommendations.isNullOrEmpty()) {
                    recommendationAdapter.submitList(detectedMealItem.recommendations)
                    recommendationAdapter.notifyDataSetChanged()
                }
            } else if (detectedMealItem is RecommendationsMeal) {
                binding.imgCapture.setImageFromUrl(detectedMealItem.imageUrl ?: "")
            }
        }
    }

    fun setDetectedMealItem(mealItem: DetectedMealItem?, image: Bitmap) {
        componentsSetup(mealItem, image)
    }

    fun setRecommendationItem(recommendationsMeal: RecommendationsMeal) {
        componentsSetup(recommendationsMeal)
    }

    fun showDialog() {
        glycemixBottomMenu.show()
    }
}