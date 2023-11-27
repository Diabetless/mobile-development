package com.ch2Ps073.diabetless.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.data.remote.response.Article
import com.ch2Ps073.diabetless.utils.Event
import com.ch2Ps073.diabetless.utils.Result
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: String,
    private val repository: UserRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailArticle = MutableLiveData<Article>()
    val detailArticle: LiveData<Article> = _detailArticle

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    init {
        getDetailArticle(id)
    }

    private fun getDetailArticle(id: String) {
        viewModelScope.launch {
            repository.getDetailArticle(id).asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }

                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _detailArticle.postValue(result.data.article!!)
                    }

                    is Result.Error -> {
                        _isLoading.postValue(false)
                        _toastMessage.postValue(Event(result.error))
                    }
                }
            }
        }
    }

}