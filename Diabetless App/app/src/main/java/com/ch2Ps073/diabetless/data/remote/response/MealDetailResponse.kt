package com.ch2Ps073.diabetless.data.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MealDetailResponse(

	@field:SerializedName("meal")
	val meal: Meal? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Entity("favorited_meal")
data class Meal(

	@field:SerializedName("glycemicLoad")
	val glycemicLoad: Double,

	@field:SerializedName("carbs")
	val carbs: Double,

	@field:SerializedName("fats")
	val fats: Double,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("calorie")
	val calorie: Double,

	@field:SerializedName("postDate")
	val postDate: String? = null,

	@PrimaryKey
	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("glycemicIndex")
	val glycemicIndex: Double
)

@Entity("meal_cart")
data class MealCart(

	@field:SerializedName("glycemicLoad")
	val glycemicLoad: Double,

	@field:SerializedName("carbs")
	val carbs: Double,

	@field:SerializedName("fats")
	val fats: Double,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("calorie")
	val calorie: Double,

	@field:SerializedName("postDate")
	val postDate: String? = null,

	@PrimaryKey
	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("glycemicIndex")
	val glycemicIndex: Double
)

