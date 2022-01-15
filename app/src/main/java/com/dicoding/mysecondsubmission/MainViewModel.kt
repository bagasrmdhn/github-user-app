package com.dicoding.mysecondsubmission


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<User>>()
    fun getListUsers(): LiveData<ArrayList<User>> = listUsers

    private val inLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = inLoading


    fun searchUser(query: String) {
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UserResponse> {


            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                inLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        listUsers.postValue(response.body()?.items)
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                inLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
