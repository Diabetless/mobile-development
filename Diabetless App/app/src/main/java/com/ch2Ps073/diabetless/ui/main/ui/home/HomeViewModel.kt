package com.ch2Ps073.diabetless.ui.main.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.remote.ApiConfig
import com.ch2Ps073.diabetless.data.remote.response.ArticleItem
import com.ch2Ps073.diabetless.data.remote.response.ArticlesResponse
import com.ch2Ps073.diabetless.data.remote.response.DetailUser
import com.ch2Ps073.diabetless.data.remote.response.ListUser
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.ProfileSettingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleItem?>?>()
    val articles: LiveData<List<ArticleItem?>?> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    suspend fun getAllArticles() {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getAllArticles()
//        client.enqueue(object : Callback<ArticlesResponse> {
//
//            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e("HomeViewModel", "onFailure: getDetailUser fail 1")
//            }
//
//            override fun onResponse(
//                call: Call<ArticlesResponse>,
//                response: Response<ArticlesResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        _articles.value = responseBody.article
//                    }
//                } else {
//                    Log.e("HomeViewModel", "onFailure: getDetailUser fail 2")
//                }
//
//            }
//        })
//    }
}