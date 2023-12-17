package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2Ps073.diabetless.data.remote.ApiConfig
import com.ch2Ps073.diabetless.data.remote.ApiService
import com.ch2Ps073.diabetless.data.remote.response.DetectedMealItem
import com.ch2Ps073.diabetless.data.remote.response.DetectedMealResponse
import com.ch2Ps073.diabetless.utils.Result
import com.ch2Ps073.diabetless.utils.SingleLiveData
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File


class GlycemicViewModel : ViewModel() {
    private val _detectedMeal = SingleLiveData<Result<Pair<DetectedMealItem?, Bitmap>>>()

    val detectedMeal: LiveData<Result<Pair<DetectedMealItem?, Bitmap>>?>
        get() = _detectedMeal

    private val apiService: ApiService = ApiConfig.getApiService()

    fun detectMealNutrition(imageFile: File, imageBitmap: Bitmap) {
        viewModelScope.launch {
            _detectedMeal.value = Result.Loading

            try {
                val imageRequestBody =
                    imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                val multipartBuilder = MultipartBody.Builder()
                multipartBuilder.setType(MultipartBody.FORM)
                multipartBuilder.addFormDataPart("image", imageFile.name, imageRequestBody)

                val body =
                    MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)

                val response = apiService.getDetectedMeals(body)
                if (response.result != null) {
                    val result = response.result
                    if (result.isNotEmpty() && result.first() != null) {
                        val mealItem = result.first()
                        mealItem?.nutritionFact?.glLevel = glLevel(mealItem?.nutritionFact?.gl ?: 0)
                        mealItem?.nutritionFact?.giLevel = glLevel(mealItem?.nutritionFact?.gi ?: 0)

                        _detectedMeal.value = Result.Success(Pair(mealItem, imageBitmap))
                    } else {
                        // determine analysis is empty
                        _detectedMeal.value = Result.Success(Pair(null, imageBitmap))
                    }
                } else {
                    _detectedMeal.value = Result.Error(response.message ?: "Terjadi Kesalahan")
                }
            } catch (e: HttpException) {
                e.printStackTrace()

                val errorBody = e.response()?.errorBody()?.string()

                _detectedMeal.value = Result.Error("Terjadi Kesalahan")
            }
        }
    }

    private fun glLevel(gl: Number): String {
        return if (gl.toDouble() in 0.0..10.0) {
            "Low"
        } else if (gl.toDouble() > 10 && gl.toDouble() <= 19) {
            "Moderate"
        } else if (gl.toDouble() >= 20) {
            "High"
        } else {
            "Unknown"
        }
    }

    private fun giLevel(gi: Number): String {
        return if (gi.toDouble() <= 55) {
            "Low"
        } else if (gi.toDouble() > 55 && gi.toDouble() < 70) {
            "Moderate"
        } else if (gi.toDouble() >= 70) {
            "High"
        } else {
            "Unknown"
        }
    }
}
