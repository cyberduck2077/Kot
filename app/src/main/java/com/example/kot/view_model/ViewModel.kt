package com.example.kot.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kot.model.SimpleDataModel
import com.example.kot.model.user.UserDataModel
import com.example.kot.repository.UserRepository
import kotlinx.coroutines.*

class SimpleViewModel constructor(private val userRepository: UserRepository) : ViewModel() {

    val dataModelLiveData = MutableLiveData<SimpleDataModel>()
    val userModelLiveData = MutableLiveData<UserDataModel>()
//    val idParamLiveData = MutableLiveData<Int>()
    val numLiveData = MutableLiveData<Int>()
    val isSuccessLoadingLiveData  = MutableLiveData<Boolean>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("ERROR COROUTINE!", throwable.localizedMessage as String)
    }

    fun getUser(id:Int) {

        job = CoroutineScope(Dispatchers.IO).launch {

            val response = userRepository.getUserById(id)

            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    userModelLiveData.value = response.body()
                    isSuccessLoadingLiveData.value = true
//                    Log.d("CODE", response.code().toString())
//                    Log.d("BODY().TITLE", response.body()?.title.toString())
                } else {
                    Log.d("WWW", "Response is not success")
                    isSuccessLoadingLiveData.value = false

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
    }
}