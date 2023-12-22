package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2Ps073.diabetless.data.remote.response.DetectedMealItem
import com.ch2Ps073.diabetless.data.remote.response.FileUploadResponse
import com.ch2Ps073.diabetless.data.remote.retrofit.ApiConfigDF
import com.ch2Ps073.diabetless.data.remote.retrofit.ApiService
import com.ch2Ps073.diabetless.utils.Result
import com.ch2Ps073.diabetless.utils.SingleLiveData
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File


class GlycemicViewModel : ViewModel() {
    private val _detectedMeal = SingleLiveData<Result<Pair<List<DetectedMealItem>?, Bitmap>>>()

    val detectedMeal: LiveData<Result<Pair<List<DetectedMealItem>?, Bitmap>>?>
        get() = _detectedMeal

    private val apiService: ApiService = ApiConfigDF.getApiService()

    fun detectMealNutrition(token : String, imageFile: File, imageBitmap: Bitmap) {
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

                val response = apiService.getDetectedMeals(token, body)
                if (response.result != null) {
                    val result = response.result
                    if (result.isNotEmpty()) {
                        val items = result.map {
                            it?.nutritionFact?.glLevel = glLevel(it?.nutritionFact?.gl ?: 0)
                            it?.nutritionFact?.giLevel = giLevel(it?.nutritionFact?.gi ?: 0)
                            it!!
                        }.toList()
                        _detectedMeal.value = Result.Success(Pair(items, imageBitmap))
                    } else {
                        _detectedMeal.value = Result.Success(Pair(null, imageBitmap))
                    }
                } else {
                    _detectedMeal.value = Result.Error(response.message ?: "Terjadi Kesalahan 1")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
                Log.e("GlycemicViewModel", errorBody ?: "$errorResponse")
                _detectedMeal.value = Result.Error("Terjadi Kesalahan 2")
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
            "High"
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
            "High"
        }
    }
}
