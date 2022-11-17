package com.example.kot.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kot.model.user.UserDataModel
import com.example.kot.repository.ListUserRepository
import kotlinx.coroutines.*

class SecondViewModel constructor(private val userRepository: ListUserRepository) : ViewModel() {

    val listUsersLiveData = MutableLiveData<List<UserDataModel>>()

    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("ERROR COROUTINE!", throwable.localizedMessage as String)
    }


    fun getListUser() {

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = userRepository.getUserList()

//            MainThread - delay()!!!
            withContext(Dispatchers.Main + exceptionHandler){
                for (i in 3 downTo 0) {
                    delay(timeMillis = 1000L)
                    Log.d("TIMER!!!!!!!!", "timer: $i")
                }

                if (response.isSuccessful) {
                    listUsersLiveData.postValue(response.body())

                } else {
                    Log.d("ERROR", "Response is failed")
                }

            }


        }


    }

    override fun onCleared() {
        super.onCleared()
        job!!.cancel()
    }
}