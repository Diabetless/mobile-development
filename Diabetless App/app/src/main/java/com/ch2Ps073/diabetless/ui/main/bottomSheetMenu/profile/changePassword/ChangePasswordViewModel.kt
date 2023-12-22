package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.changePassword

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.remote.retrofit.ApiConfig
import com.ch2Ps073.diabetless.data.remote.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

@SuppressLint("StaticFieldLeak")
class ChangePasswordViewModel(private val context: Context,
                              private val lifecycleScope: LifecycleCoroutineScope
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun editPassword(token : String, oldPW : String, newPW : String){
        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.changePassword(token, oldPW, newPW)
                val toastSuccess = successResponse.message
                showToast(toastSuccess)
                Log.d("ApiResponse", toastSuccess)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                showToast(errorResponse.message)
                Log.d("ApiResponse", errorBody ?: "$errorResponse")
            }
        }
        _isLoading.postValue(false)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}