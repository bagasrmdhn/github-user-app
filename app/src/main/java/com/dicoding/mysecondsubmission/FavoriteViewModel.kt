package com.dicoding.mysecondsubmission

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.favoriteUserDao()
    }

    fun getFavorite(): LiveData<List<FavoriteUser>>?{
        return  userDao?.getFavorite()
    }
}