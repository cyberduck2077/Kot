package com.example.kot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kot.adapters.RecyclerAdapter
import com.example.kot.databinding.ActivitySecondBinding
import com.example.kot.repository.ListUserRepository
import com.example.kot.repository.SimpleDataRepository
import com.example.kot.repository.UserRepository
import com.example.kot.repository.retrofit.RetrofitService
import com.example.kot.view_model.SecondViewModel
import com.example.kot.view_model.ViewModelFactory

class SecondActivity : AppCompatActivity() {

    lateinit var bind: ActivitySecondBinding

    lateinit var mSecondViewModel: SecondViewModel

    lateinit var mUserRepository: UserRepository
    lateinit var mListUserRepository: ListUserRepository
    lateinit var mSimpleDataRepository: SimpleDataRepository

    lateinit var mRetrofitService: RetrofitService

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(bind.root)

        recyclerView = bind.rvUsers

        mRetrofitService = RetrofitService.getInstance()

        mSimpleDataRepository = SimpleDataRepository(mRetrofitService)
        mUserRepository = UserRepository(mRetrofitService)
        mListUserRepository = ListUserRepository(mRetrofitService)

        mSecondViewModel = ViewModelProvider(this,
            ViewModelFactory(mUserRepository, mSimpleDataRepository, mListUserRepository)).get(
            SecondViewModel::class.java)

        mSecondViewModel.getListUser()

        recyclerView.layoutManager = LinearLayoutManager(this)



        mSecondViewModel.listUsersLiveData.observe(this, Observer {
            recyclerView.adapter = RecyclerAdapter(it)
        })

    }
}