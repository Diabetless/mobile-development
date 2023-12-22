package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.content.Context
import android.view.LayoutInflater
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.RecommendationsMeal
import com.ch2Ps073.diabetless.databinding.DialogRecommendationMealBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class RecommendationMealDialog(
    private val context: Context
) {

    private var binding: DialogRecommendationMealBinding =
        DialogRecommendationMealBinding.inflate(LayoutInflater.from(context))

    private var bottomSheetDialog: BottomSheetDialog =
        BottomSheetDialog(context, R.style.SheetDialog).apply {
            setContentView(binding.root)
        }

    fun setRecommendationItem(recommendationsMeal: RecommendationsMeal) {
        binding.glycemicIndex.setComponents(context, recommendationsMeal)
        //binding..setComponents(context, recommendationsMeal)
    }

    fun showDialog() {
        bottomSheetDialog.show()
    }
}