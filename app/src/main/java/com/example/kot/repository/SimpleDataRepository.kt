package com.example.kot.repository

import com.example.kot.repository.retrofit.RetrofitService

class SimpleDataRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getSimpleDataById(num: Int) = retrofitService.getSimpleData(num)
}