package com.ch2Ps073.diabetless.ui.main.ui.health.bodyComposition

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.remote.ApiConfig
import com.ch2Ps073.diabetless.data.remote.response.FileUploadResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

@SuppressLint("StaticFieldLeak")
class BodyCompositionViewModel(
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope,
) : ViewModel() {

    fun setBody(token: String, height : Int, weight : Int) {

        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.setBody(token, height, weight)
                val toastSuccess = successResponse.message
                showToast(toastSuccess)
            } catch (e: HttpException) {
                handleHttpException(e)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleHttpException(exception: HttpException) {
        val errorBody = exception.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
        showToast(errorResponse.message)
        Log.d("ApiResponse", errorBody ?: "$errorResponse")
    }
}