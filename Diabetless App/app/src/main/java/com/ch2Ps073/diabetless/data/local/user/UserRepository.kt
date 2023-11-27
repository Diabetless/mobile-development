package com.ch2Ps073.diabetless.data.local.user.pref

import androidx.lifecycle.liveData
import com.ch2Ps073.diabetless.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import com.ch2Ps073.diabetless.utils.Result

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    fun getArticles() = liveData {
        emit(Result.Loading)
        try {
            val articlesResponse = apiService.getAllArticles()
            emit(Result.Success(articlesResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailArticle(id: String) = liveData {
        emit(Result.Loading)
        try {
            val detailArticleResponse = apiService.getDetailArticle(id)
            emit(Result.Success(detailArticleResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}