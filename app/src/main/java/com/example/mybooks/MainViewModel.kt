package com.example.mybooks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val itemList = MutableLiveData<List<BookItem>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllItems() {

        val response = repository.getAllItems()
        response.enqueue(object : Callback<List<BookItem>> {
            override fun onResponse(call: Call<List<BookItem>>, response: Response<List<BookItem>>) {
                itemList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<BookItem>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}