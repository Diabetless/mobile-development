package com.ch2Ps073.diabetless.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2Ps073.diabetless.data.local.user.pref.UserModel
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.data.remote.response.FileUploadResponse
import com.ch2Ps073.diabetless.data.remote.retrofit.ApiConfig
import com.ch2Ps073.diabetless.ui.main.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

@SuppressLint("StaticFieldLeak")
class LoginViewModel(private val context: Context,
                     private val lifecycleScope: LifecycleCoroutineScope,
                     private val repository: UserRepository) : ViewModel() {

    private fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun performLogin(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.login(email, password)
                val toastSuccess = successResponse.message
                showToast(toastSuccess)
                val token = successResponse.token
                TOKEN = token

                if (TOKEN != "") {
                    saveSession(UserModel(email, "linha", "Bearer $token"))
                    showSuccessDialog()
                } else {
                    showToast(TOKEN)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
                showToast(errorResponse.message)
                Log.e("LoginViewModel", errorBody ?: "$errorResponse")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(context).apply {
            setTitle("Yeah!")
            setMessage("Anda berhasil login.")
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            create()
            show()
        }
    }

    companion object {
        var TOKEN = "token"
    }

}