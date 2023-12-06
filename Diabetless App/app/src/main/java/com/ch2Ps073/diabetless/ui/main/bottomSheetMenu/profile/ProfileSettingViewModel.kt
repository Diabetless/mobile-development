package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.local.user.pref.UserModel
import com.ch2Ps073.diabetless.data.remote.ApiConfig
import com.ch2Ps073.diabetless.data.remote.response.DetailUser
import com.ch2Ps073.diabetless.data.remote.response.ListUser
import com.ch2Ps073.diabetless.data.remote.response.RegisterResponse
import com.ch2Ps073.diabetless.data.remote.response.UpdateUserResponse
import com.ch2Ps073.diabetless.ui.login.LoginViewModel
import com.ch2Ps073.diabetless.ui.main.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

@SuppressLint("StaticFieldLeak")
class ProfileSettingViewModel(private val context: Context,
                              private val lifecycleScope: LifecycleCoroutineScope,
    ) : ViewModel() {

    private val _users = MutableLiveData<DetailUser>()
    val users: LiveData<DetailUser> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(token : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(token)
        client.enqueue(object : Callback<ListUser> {

            override fun onFailure(call: Call<ListUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: getDetailUser fail 1")
            }

            override fun onResponse(
                call: Call<ListUser>,
                response: Response<ListUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _users.value = responseBody.user ?: DetailUser()
                    }
                } else {
                    Log.e(TAG, "onFailure: getDetailUser fail 2")
                }

            }
        })
    }

    fun editProfile(token: String, name: String, email: String, imageFile: File, username: String) {

        val fullNameR = name.toRequestBody(MultipartBody.FORM)
        val usernameR = email.toRequestBody(MultipartBody.FORM)
        val emailR = username.toRequestBody(MultipartBody.FORM)


        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )

        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse =
                    apiService.updateUser(token, fullNameR, usernameR, multipartBody, emailR)
                val toastSuccess = successResponse.message
                showToast(toastSuccess)
                showSuccessDialog()
            } catch (e: HttpException) {
                handleHttpException(e)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(context).apply {
            setTitle("Yeah!")
            setMessage("Edit Profile berhasil.")
            setPositiveButton("Lanjut") { _, _ ->
            }
            create()
            show()
        }
    }

    private fun handleHttpException(exception: HttpException) {
        val errorBody = exception.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, UpdateUserResponse::class.java)
        Log.d("ApiResponse", errorBody ?: "$errorResponse")
        AlertDialog.Builder(context).apply {
            setTitle("Edit Profile gagal!")
            setMessage("coba hubungi help center \n$errorResponse")
            setPositiveButton("coba lagi") { _, _ ->
            }
            create()
            show()
        }
    }

    companion object{
        private const val TAG = "ProfileSettingViewModel"
    }

}