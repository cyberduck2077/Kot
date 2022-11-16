package com.example.kot.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kot.repository.UserRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SimpleViewModel::class.java)){
            SimpleViewModel(this.repository) as T
        }
        else{
            throw IllegalArgumentException("View Not Found")
        }
    }
}