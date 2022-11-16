package com.example.kot.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kot.model.SimpleDataModel
import com.example.kot.repository.retrofit.RetrofitHelper
import com.example.kot.repository.retrofit.SimpleDataApiInterface
import kotlinx.coroutines.*

class SimpleViewModel(var num: Int = 0) : ViewModel() {

    val dataModelLiveData = MutableLiveData<SimpleDataModel>()
    val numLiveData = MutableLiveData<Int>()
    var job: Job? = null

    fun getAllData() {

        val dataApi = RetrofitHelper.getInstance().create(SimpleDataApiInterface::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = dataApi.getSimpleData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    dataModelLiveData.postValue(response.body())
//                    Log.d("CODE", response.code().toString())
//                    Log.d("BODY().TITLE", response.body()?.title.toString())
                } else {
                    Log.d("WWW", "Response is not success")
                }
            }
        }
    }

    fun showNum() {
        val _num = if (numLiveData.value != null) numLiveData.value else num

        Log.d("DDD", numLiveData.value.toString())
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}