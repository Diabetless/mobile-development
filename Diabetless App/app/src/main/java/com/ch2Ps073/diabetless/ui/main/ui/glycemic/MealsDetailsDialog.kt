package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.BaseDetectedMeal
import com.ch2Ps073.diabetless.data.remote.response.DetectedMealItem
import com.ch2Ps073.diabetless.data.remote.response.RecommendationsMeal
import com.ch2Ps073.diabetless.databinding.BottomSheetGlycemixCameraLayoutBinding
import com.ch2Ps073.diabetless.databinding.ItemGlycemicIndexBinding
import com.ch2Ps073.diabetless.ui.adapter.BaseRecyclerViewBindingAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog


class MealsDetailsDialog(
    private val context: Context,
    private val onDismiss: (DialogInterface) -> Unit,
    private val onClickRecommendationsMeal: ((RecommendationsMeal) -> Unit)? = null,
) {

    private var mealItems: MutableList<DetectedMealItem> = mutableListOf()
    private var imageCaptureBitmap: Bitmap? = null

    private var dialogBinding: BottomSheetGlycemixCameraLayoutBinding =
        BottomSheetGlycemixCameraLayoutBinding.inflate(LayoutInflater.from(context)).apply {
            lyResult.visibility = View.VISIBLE
            lyNoResult.visibility = View.GONE
        }

    private var glycemixBottomMenu: BottomSheetDialog =
        BottomSheetDialog(context, R.style.SheetDialog).apply {
            setContentView(dialogBinding.root)
            setOnDismissListener { d ->
                dialogBinding.root.invalidate()
                onDismiss.invoke(d)
            }
        }

    private val viewPagerAdapter by lazy {
        object : BaseRecyclerViewBindingAdapter<ItemGlycemicIndexBinding, BaseDetectedMeal>(
            ItemGlycemicIndexBinding::inflate,
            bind = { item, _, _, binding ->
                binding.setupRvRecommendation(onClickRecommendationsMeal)
                binding.setComponents(context, item, imageCaptureBitmap)
            }
        ) {}
    }

    init {

        dialogBinding.imgPrev.setOnClickListener {
            if (dialogBinding.viewPager2.currentItem > 0) {
                dialogBinding.viewPager2.currentItem = dialogBinding.viewPager2.currentItem - 1
            }
        }
        dialogBinding.imgNext.setOnClickListener {
            if (dialogBinding.viewPager2.currentItem < dialogBinding.viewPager2.adapter!!.itemCount) {
                dialogBinding.viewPager2.currentItem = dialogBinding.viewPager2.currentItem + 1
            }
        }

        dialogBinding.viewPager2.adapter = viewPagerAdapter
        dialogBinding.viewPager2.isUserInputEnabled = false
        dialogBinding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dialogBinding.tvCountMeal.text = "${position + 1} / ${mealItems.size}"
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    fun setMealItems(mealItems: MutableList<DetectedMealItem>?, imageCaptureBitmap: Bitmap?) {
        this.imageCaptureBitmap = imageCaptureBitmap

        if (!mealItems.isNullOrEmpty()) {
            dialogBinding.lyCount.visibility = if (mealItems.size > 1) View.VISIBLE else View.GONE
            dialogBinding.lyResult.visibility = View.VISIBLE
            dialogBinding.lyNoResult.visibility = View.GONE

            this.mealItems = mealItems
            viewPagerAdapter.submitList(mealItems as MutableList<BaseDetectedMeal>)
            viewPagerAdapter.notifyDataSetChanged()

            dialogBinding.tvCountMeal.text = "1 / ${mealItems.size}"
        } else {
            dialogBinding.lyCount.visibility = View.GONE
            dialogBinding.lyResult.visibility = View.GONE
            dialogBinding.lyNoResult.visibility = View.VISIBLE

            dialogBinding.imgCaptureNoResult.setImageBitmap(imageCaptureBitmap)
        }
    }

    fun setDetectedMealItem(mealItem: DetectedMealItem?, image: Bitmap) {
        //componentsSetup(mealItem, image)
    }

    fun showDialog() {
        glycemixBottomMenu.show()
    }
}