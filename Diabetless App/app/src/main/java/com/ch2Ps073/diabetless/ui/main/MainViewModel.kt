package com.ch2Ps073.diabetless.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch2Ps073.diabetless.data.local.user.pref.UserModel
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.data.remote.response.ArticleItem
import com.ch2Ps073.diabetless.utils.Event
import com.ch2Ps073.diabetless.utils.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _allArticles = MutableLiveData<ArrayList<ArticleItem>>()
    val allArticles: LiveData<ArrayList<ArticleItem>> = _allArticles

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    init {
        getAllArticles()
    }

    private fun getAllArticles() {
        viewModelScope.launch {
            repository.getArticles().asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }

                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _allArticles.postValue(result.data.article as ArrayList<ArticleItem>?)
                    }

                    is Result.Error -> {
                        _isLoading.postValue(false)
                        _toastMessage.postValue(Event(result.error))
                    }
                }
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}