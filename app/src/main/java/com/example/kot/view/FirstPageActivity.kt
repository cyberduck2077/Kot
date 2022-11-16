package com.example.kot.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kot.databinding.ActivityFirstPageBinding
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

        mSimpleViewModel = ViewModelProvider(this, ViewModelFactory(userRepository))
            .get(SimpleViewModel::class.java)

        bind.downloadBottom.setOnClickListener {

//            val s = bind.editText.text.toString()
//            if (s.isNotEmpty()) mSimpleViewModel.numLiveData.value = s.toInt()

//            val s = bind.editText.text.toString()
//            val idUser = if(s.isNotEmpty() && s.toInt()>0) s.toInt() else 0
//

//            mSimpleViewModel.getUser(idUser)
//            mSimpleViewModel.userModelLiveData.observe(this, Observer {
//                bind.cardText1.text = "name: ${it.name}"
//                bind.cardText2.text = "username: ${it.username}"
//                bind.cardText3.text = "email: ${it.email}"
//                bind.cardText4.text = "company name: ${it.company.name}"
//                bind.cardText5.text = "city: ${it.address.city}"
//            })
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

