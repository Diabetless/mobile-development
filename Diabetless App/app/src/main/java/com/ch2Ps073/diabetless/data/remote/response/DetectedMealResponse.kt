package com.ch2Ps073.diabetless.data.remote.response

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class DetectedMealResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("result")
    val result: List<DetectedMealItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)

abstract class BaseDetectedMeal {
    abstract val name: String?

    abstract val type: String?

    abstract val nutritionFact: NutritionFact?

    abstract val tags: List<String>?

    abstract val serving: String?

    var imageBitmap: Bitmap? = null
}

data class DetectedMealItem(
    @field:SerializedName("name")
    override val name: String? = null,

    @field:SerializedName("type")
    override val type: String? = null,

    @field:SerializedName("nutrition_fact")
    override val nutritionFact: NutritionFact? = null,

    @field:SerializedName("tags")
    override val tags: List<String>? = null,

    @field:SerializedName("serving")
    override val serving: String? = null,

    @field:SerializedName("recommendation")
    val recommendations: List<RecommendationsMeal>? = null,
) : BaseDetectedMeal() {
    companion object {
        val dummy = arrayOf(
            DetectedMealItem(
                name = "test 1"
            ),
            DetectedMealItem(
                name = "test 2"
            ),
            DetectedMealItem(
                name = "test 3"
            )
        )
    }
}


data class NutritionFact(
    /**
     * @prefer Int
     */
    @field:SerializedName("GL")
    val gl: Number? = null,

    @field:SerializedName("GL_Level")
    var glLevel: String? = null,

    /**
     * @prefer Int
     */
    @field:SerializedName("GI")
    val gi: Number? = null,

    @field:SerializedName("GI_Level")
    var giLevel: String? = null,

    /**
     * @prefer Double
     */
    @field:SerializedName("Proteins")
    val proteins: Number? = null,

    /**
     * @prefer Int
     */
    @field:SerializedName("Carbohydrates")
    val carbohydrates: Number? = null,

    /**
     * @prefer Double
     */
    @field:SerializedName("Fats")
    val fats: Number? = null,

    /**
     * @prefer Int
     */
    @field:SerializedName("Calories")
    val calories: Number? = null,
)

data class RecommendationsMeal(
    @field:SerializedName("name")
    override val name: String? = null,

    @field:SerializedName("type")
    override val type: String? = null,

    @field:SerializedName("nutrition_fact")
    override val nutritionFact: NutritionFact? = null,

    @field:SerializedName("tags")
    override val tags: List<String>? = null,

    @field:SerializedName("serving")
    override val serving: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,
) : BaseDetectedMeal()