package com.example.kot.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kot.databinding.ActivityFirstPageBinding
import com.example.kot.repository.retrofit.RetrofitHelper
import com.example.kot.repository.retrofit.SimpleDataApiInterface
import com.example.kot.view_model.SimpleViewModel
import com.example.kot.view_model.ViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirstPageActivity : AppCompatActivity() {

    lateinit var bind: ActivityFirstPageBinding
    lateinit var mSimpleViewModel: SimpleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView(bind.root)

        mSimpleViewModel = ViewModelProvider(this, ViewModelFactory(2))
            .get(SimpleViewModel::class.java)

        bind.downloadBottom.setOnClickListener {
//            startJob()

            val s = bind.editText.text.toString()
            if (s.isNotEmpty()) mSimpleViewModel.numLiveData.value = s.toInt()

            mSimpleViewModel.showNum()
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

