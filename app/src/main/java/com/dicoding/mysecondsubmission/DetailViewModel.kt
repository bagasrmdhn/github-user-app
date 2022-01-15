package com.dicoding.mysecondsubmission

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val detailUsers = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.favoriteUserDao()
    }


    fun getDetailUsers(): LiveData<DetailUserResponse>{
        return detailUsers
    }

    private val inLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = inLoading


    fun setUserDetail(username: String) {
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                inLoading.value = false
                if (response.isSuccessful) {
                    detailUsers.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                inLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username, id, avatarUrl)
            userDao?.addFavortie(user)
        }
    }

    suspend fun userCheck(id: Int) = userDao?.userCheck(id)

    fun deleteFromFavortie(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.userDelete(id)
        }
    }
}
