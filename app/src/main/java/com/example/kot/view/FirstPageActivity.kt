package com.example.kot.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kot.databinding.ActivityFirstPageBinding
import com.example.kot.repository.retrofit.RetrofitHelper
import com.example.kot.repository.retrofit.SimpleDataApiInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirstPageActivity : AppCompatActivity() {

    lateinit var bind: ActivityFirstPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.downloadBottom.setOnClickListener {
            startJob()
        }
    }

    @DelicateCoroutinesApi
    private fun startJob() {
        val dataApi = RetrofitHelper.getInstance().create(SimpleDataApiInterface::class.java)

        GlobalScope.launch {
            val result = dataApi.getSimpleData()
            Log.d("CODE", result.code().toString())
            Log.d("BODY().TITLE", result.body()?.title.toString())
        }

    }



}

