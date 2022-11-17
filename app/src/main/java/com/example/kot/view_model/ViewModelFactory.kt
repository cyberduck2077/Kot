package com.example.kot.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kot.repository.ListUserRepository
import com.example.kot.repository.SimpleDataRepository
import com.example.kot.repository.UserRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(userRepository: UserRepository, simpleDataRepository: SimpleDataRepository) :
    ViewModelProvider.Factory {

    var _simpleDataRepository = simpleDataRepository
    var _userRepository = userRepository
    lateinit var _userListRepository: ListUserRepository
    constructor(
        userRepository: UserRepository, simpleDataRepository: SimpleDataRepository,
        userListRepository: ListUserRepository,
    ) : this(userRepository, simpleDataRepository){

        _userListRepository = userListRepository
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SimpleViewModel::class.java)) {
            SimpleViewModel(this._userRepository, this._simpleDataRepository) as T
        } else if (modelClass.isAssignableFrom(SecondViewModel::class.java) &&_userListRepository!=null) {
            SecondViewModel(_userListRepository) as T
        } else {
            throw IllegalArgumentException("View Not Found")
        }
    }
}