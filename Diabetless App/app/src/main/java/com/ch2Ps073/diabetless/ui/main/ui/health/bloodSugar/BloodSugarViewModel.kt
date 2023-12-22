package com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.remote.retrofit.ApiConfig
import com.ch2Ps073.diabetless.data.remote.response.FileUploadResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

@SuppressLint("StaticFieldLeak")
class BloodSugarViewModel(
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope,
) : ViewModel() {

    fun setBloodSL(token: String, blood : Int) {

        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.setBloodSL(token, blood)
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