package com.ch2Ps073.diabetless.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HealthUserResponse(

	@field:SerializedName("bloodSugarData")
	val bloodSugarData: List<BloodSugarDataItem?>? = null,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("BMIData")
	val bMIData: List<BMIDataItem?>? = null,

	@field:SerializedName("status")
	val status: String,

) : Parcelable

@Parcelize
data class BloodSugarDataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("level")
	val level: Int? = null

) : Parcelable

@Parcelize
data class BMIDataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null

) : Parcelable
