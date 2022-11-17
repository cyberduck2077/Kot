package com.example.kot.repository.retrofit

import com.example.kot.model.SimpleDataModel
import com.example.kot.model.user.UserDataModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("todos/{num}")
    suspend fun getSimpleData(@Path("num") num: Int): Response<SimpleDataModel>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<UserDataModel>

    @GET("users/")
    suspend fun getListUsers():Response<List<UserDataModel>>

    companion object {
        val baseUrl = "https://jsonplaceholder.typicode.com/"
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }

}