package com.dicoding.mysecondsubmission


import retrofit2.Call
import retrofit2.http.*


// token : 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
interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
        ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}
