package com.example.kot.repository.retrofit

import com.example.kot.model.SimpleDataModel
import retrofit2.Response
import retrofit2.http.GET

interface SimpleDataApiInterface {

    @GET("1")
    suspend fun getSimpleData():Response<SimpleDataModel>
}