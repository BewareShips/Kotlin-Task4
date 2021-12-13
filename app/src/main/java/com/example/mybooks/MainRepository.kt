package com.example.mybooks

import retrofit2.Response

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllItems() = retrofitService.getAllItems()
}