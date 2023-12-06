package com.ch2Ps073.diabetless.data.remote.response

import com.google.gson.annotations.SerializedName

data class MealsResponse(

	@field:SerializedName("meal")
	val meal: List<MealItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class MealItem(

	@field:SerializedName("glycemicLoad")
	val glycemicLoad: Double? = null,

	@field:SerializedName("carbs")
	val carbs: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("calorie")
	val calorie: Double? = null,

	@field:SerializedName("postDate")
	val postDate: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("glycemicIndex")
	val glycemicIndex: Double? = null
)
