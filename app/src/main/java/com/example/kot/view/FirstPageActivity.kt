package com.example.kot.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kot.databinding.ActivityFirstPageBinding
import com.example.kot.repository.SimpleDataRepository
import com.example.kot.repository.UserRepository
import com.example.kot.repository.retrofit.RetrofitService
import com.example.kot.view_model.SimpleViewModel
import com.example.kot.view_model.ViewModelFactory


class FirstPageActivity : AppCompatActivity() {

    lateinit var bind: ActivityFirstPageBinding
    lateinit var mSimpleViewModel: SimpleViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val simpleDataApiInterface = RetrofitService.getInstance()
        val userRepository = UserRepository(simpleDataApiInterface)
        val simpleDataRepository = SimpleDataRepository(simpleDataApiInterface)

        mSimpleViewModel =
            ViewModelProvider(this, ViewModelFactory(userRepository, simpleDataRepository))
                .get(SimpleViewModel::class.java)

        var numForSearch: Int = 0

        bind.downloadBottom.setOnClickListener {

            mSimpleViewModel.getSimpleData(numForSearch)

            mSimpleViewModel.dataModelLiveData.observe(this, Observer {
                Toast.makeText(this, "for id = ${it.id} title = ${it.title}", Toast.LENGTH_SHORT).show()
            })

            if (numForSearch > 10)
                numForSearch = 0
            else
                numForSearch++
        }

        bind.editText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val s = bind.editText.text.toString()
                val idUser = if (s.isNotEmpty() && s.toInt() > 0) s.toInt() else 0

                if (idUser > 10)
                    Toast.makeText(this, "count user = 10!", Toast.LENGTH_SHORT).show()
                else
                    searchUser(idUser)

                return@OnEditorActionListener true
            }
            false
        })
    }

    @SuppressLint("SetTextI18n")
    fun searchUser(id: Int) {
        mSimpleViewModel.getUser(id)
        mSimpleViewModel.userModelLiveData.observe(this) {
            bind.cardText1.text = "name: ${it.name}"
            bind.cardText2.text = "username: ${it.username}"
            bind.cardText3.text = "email: ${it.email}"
            bind.cardText4.text = "company name: ${it.company.name}"
            bind.cardText5.text = "city: ${it.address.city}"
        }
    }


}

