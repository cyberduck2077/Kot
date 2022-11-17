package com.example.kot.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kot.model.SimpleDataModel
import com.example.kot.model.user.UserDataModel
import com.example.kot.repository.SimpleDataRepository
import com.example.kot.repository.UserRepository
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds


class SimpleViewModel constructor(
    private val userRepository: UserRepository,
    private val simpleDataRepository: SimpleDataRepository,
) : ViewModel() {

    val dataModelLiveData = MutableLiveData<SimpleDataModel>()
    val userModelLiveData = MutableLiveData<UserDataModel>()

    //    val idParamLiveData = MutableLiveData<Int>()
    val numLiveData = MutableLiveData<Int>()
    val isSuccessLoadingLiveData = MutableLiveData<Boolean>()
    var job: Job? = null
    var job2: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("ERROR COROUTINE!", throwable.localizedMessage as String)
    }

    fun getUser(id: Int) {

        job = CoroutineScope(Dispatchers.IO).launch {

            Log.d("CURRENT THREAD 1", Thread.currentThread().name)

            val response = userRepository.getUserById(id)
            delay(duration = 3.seconds)

            //если используется IO - используем liveData.postValue()
            withContext(Dispatchers.IO + exceptionHandler) {
                Log.d("CURRENT THREAD 2", Thread.currentThread().name)
                if (response.isSuccessful) {
                    userModelLiveData.postValue(response.body())
                    isSuccessLoadingLiveData.postValue(true)
//                    Log.d("CODE", response.code().toString())
//                    Log.d("BODY().TITLE", response.body()?.title.toString())
                } else {
                    Log.d("WWW", "Response is not success")
                    isSuccessLoadingLiveData.postValue(false)

                }
            }
        }
    }

    //если используется MainThread - используем liveData.setValue()
    fun getSimpleData(num: Int) {
        job2 = CoroutineScope(Dispatchers.IO).launch {
            val responce = simpleDataRepository.getSimpleDataById(num)
            delay(duration = 3.seconds)

            withContext(Dispatchers.Main + exceptionHandler) {
                if (responce.isSuccessful) {
                    dataModelLiveData.value = responce.body()
                } else {
                    Log.d("WWW", "SimpleData response is not success")
                }
            }
        }
    }

    fun showNum() {
        val _num = if (numLiveData.value != null) numLiveData.value else 0

        Log.d("DDD", numLiveData.value.toString())
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job2?.cancel()
    }
}