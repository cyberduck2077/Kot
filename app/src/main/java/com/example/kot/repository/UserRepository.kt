package com.example.kot.repository

import com.example.kot.repository.retrofit.RetrofitService

class UserRepository constructor(private val retrofitService:RetrofitService) {

    suspend fun getUserById(id:Int) = retrofitService.getUserById(id)

}