package com.ch2Ps073.diabetless.ui.main.ui.health

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.remote.retrofit.ApiConfig
import com.ch2Ps073.diabetless.data.remote.response.HealthUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HealthViewModel : ViewModel() {
    private val _healthUser = MutableLiveData<HealthUserResponse>()
    val healthUser: LiveData<HealthUserResponse> get() = _healthUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // ... Some method to update _healthUserResponse when data is available

    fun getHealthUser(token : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getHealth(token)
        client.enqueue(object : Callback<HealthUserResponse> {

            override fun onFailure(call: Call<HealthUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("HomeViewModel", "onFailure: getDetailUser fail 1")
            }

            override fun onResponse(
                call: Call<HealthUserResponse>,
                response: Response<HealthUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // You need to provide values for 'message' and 'status'
                        _healthUser.value = HealthUserResponse(
                            bloodSugarData = responseBody.bloodSugarData,
                            message = "default",
                            bMIData = responseBody.bMIData,
                            status = "default"
                        )
                    }
                } else {
                    Log.e("HomeViewModel", "onFailure: getDetailUser fail 2")
                }
            }
        })
    }
}