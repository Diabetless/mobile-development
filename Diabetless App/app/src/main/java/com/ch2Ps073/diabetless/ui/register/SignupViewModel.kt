package com.ch2Ps073.diabetless.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.remote.ApiConfig
import com.ch2Ps073.diabetless.data.remote.response.RegisterResponse
import com.ch2Ps073.diabetless.ui.login.LoginActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

@SuppressLint("StaticFieldLeak")
class SignupViewModel(private val context: Context,
                      private val lifecycleScope: LifecycleCoroutineScope
) : ViewModel() {
    fun signupUser(name: String, email: String, username: String, password: String) {
        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.register(name, email, username, password)
                val toastSuccess = successResponse.message
                showSuccessDialog(email, password, toastSuccess)
            } catch (e: HttpException) {
                handleHttpException(e)
            }
        }
    }

    private fun showSuccessDialog(email: String, password: String, toastMessage: String) {
        AlertDialog.Builder(context).apply {
            setTitle("Email dan password")
            setMessage("email anda :\"$email\" \npassword anda : \"$password\"\n$toastMessage")
            setPositiveButton("Lanjut") { _, _ ->
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
            create()
            show()
        }
    }

    private fun handleHttpException(exception: HttpException) {
        val errorBody = exception.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
        showToast(errorResponse.message)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}