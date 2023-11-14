package com.CH2PS073.diabetless.ui.main.bottomSheetMenu.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.CH2PS073.diabetless.data.remote.ApiConfig
import com.CH2PS073.diabetless.data.remote.response.DetailUser
import com.CH2PS073.diabetless.data.remote.response.ListUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileSettingViewModel : ViewModel() {

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
                // Toast.makeText(this@DetailViewModel, "findetail gagal", Toast.LENGTH_SHORT).show()
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
                    Log.e(TAG, "onFailure: findDetail fail 2")
                }

            }
        })
    }

    companion object{
        private const val TAG = "ProfileSettingViewModel"
    }

}