package com.github.api.remote

import com.github.api.data.User
import com.github.api.data.UserList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/users")
    fun listUser() : Call<List<User>>

    @GET("/users/")
    fun detailUser(@Query("user_name") url: String) : Call<UserList>

    @GET("/search/users?q=")
    fun searchUser(@Query("q") url: String) : Call<UserList>
}