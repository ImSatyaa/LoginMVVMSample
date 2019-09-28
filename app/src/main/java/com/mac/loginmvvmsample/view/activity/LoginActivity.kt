package com.mac.loginmvvmsample.view.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mac.loginmvvmsample.R
import com.mac.loginmvvmsample.base.BaseActivity
import com.mac.loginmvvmsample.databinding.ActivityLoginBinding
import com.mac.loginmvvmsample.network.ApiResponse
import com.mac.loginmvvmsample.network.Status
import com.mac.loginmvvmsample.utils.LoginApplication
import com.mac.loginmvvmsample.viewmodel.LoginViewModel
import com.mac.loginmvvmsample.viewmodel.ViewModelFactory
import java.util.*
import javax.inject.Inject






class LoginActivity : BaseActivity<ActivityLoginBinding>() {


    @Inject
    lateinit var viewModelProviders: ViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    lateinit var viewModel: LoginViewModel



    override val layoutRes: Int
        get() = R.layout.activity_login



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as LoginApplication).getAppComponent().doInjection(this)
        viewModel = ViewModelProviders.of(this, viewModelProviders).get(LoginViewModel::class.java)
        dataBinding.viewModel = viewModel
        observeData();
    }

    private fun observeData() {

        //adding observer on login button click if validation work it will return request model data
        viewModel.observeLoginRes().observe(this, Observer { data ->
            run {
                if (data != null) {
                    if (checkInternet())
                        viewModel.hitLogin(data)
                }
            }
        })


        viewModel.loginResponse.observe(this, Observer {
                data -> onLoginRes(data) })


    }

    private fun onLoginRes(apiResponse: ApiResponse) {

        //when you get response from api you can access data like {apiResponse.data}
        //for error {apiResponse.error}

        Log.e("LoginResp", "onLoginRes: " + apiResponse.data)
        when (apiResponse.status) {

            Status.LOADING -> Toast.makeText(this, "Data Loading....", Toast.LENGTH_LONG).show()

            Status.SUCCESS -> Toast.makeText(this, "Data loaded", Toast.LENGTH_LONG).show()

            Status.ERROR -> {
                // moving to dashboard cuz its just a demo api so error is going to occur every time
                 moveToDashboard()

            }

        }

    }

    //add code to check internet connection before hitting api
    private fun checkInternet(): Boolean {
        return true
    }

    //after success move to dashboard
    private fun moveToDashboard(){
        startActivity(Intent(this,MainActivity::class.java))
    }




}
