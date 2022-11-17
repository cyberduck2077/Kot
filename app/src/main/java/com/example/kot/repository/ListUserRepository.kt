package com.example.kot.repository

import com.example.kot.repository.retrofit.RetrofitService

class ListUserRepository constructor(val service: RetrofitService) {

    suspend fun getUserList() = service.getListUsers()
}