package com.dicoding.mysecondsubmission

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteUserDao {
    @Insert
    fun addFavortie(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun userCheck(id:Int):Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id  = :id")
    fun userDelete(id: Int):Int
}
