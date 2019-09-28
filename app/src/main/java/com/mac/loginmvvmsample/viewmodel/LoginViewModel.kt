package com.mac.loginmvvmsample.viewmodel

import android.util.Log
import android.view.View
import android.widget.RadioGroup

import com.google.gson.Gson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mac.loginmvvmsample.models.LoginRequestModel
import com.mac.loginmvvmsample.network.ApiResponse
import com.mac.loginmvvmsample.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val disposables = CompositeDisposable()

    var loginModel: LoginRequestModel? = null
        private set
    var userIdFocusChangeListener: View.OnFocusChangeListener? = null
        private set
    var passFocusChangeListener: View.OnFocusChangeListener? = null
        private set
    private val buttonClick = MutableLiveData<LoginRequestModel>()
    val loginResponse = MutableLiveData<ApiResponse>()
     val checkedRId = MutableLiveData<Int>()



    init {
        loginModel = LoginRequestModel()
        userIdFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                loginModel!!.checkIdValid(true)
            }
        }

        passFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                loginModel!!.checkPassValid(true)
            }
        }
    }


    fun observeLoginRes(): MutableLiveData<LoginRequestModel> {
        return buttonClick
    }

    fun hitLogin(loginRequestModel: LoginRequestModel) {
        disposables.add(repository.executeLogin(loginRequestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d -> loginResponse.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> loginResponse.setValue(ApiResponse.success(result)) },
                { throwable -> loginResponse.setValue(ApiResponse.error(throwable)) }
            ))
    }

    override fun onCleared() {
        disposables.clear()
    }


    fun onLoginClick() {
        if (loginModel!!.isIdValid && loginModel!!.isPassValid) {
            buttonClick.setValue(loginModel)
        } else {
            if (!loginModel!!.isIdValid) {
                loginModel!!.checkIdValid(true)
            } else if (!loginModel!!.isPassValid) {
                loginModel!!.checkPassValid(true)
            }
        }
    }




    fun onChangeLangClick(): MutableLiveData<Int> {
        if (checkedRId.value ==null || checkedRId.value == 1){
            checkedRId.value = 0
        }else{
            checkedRId.value = 1
        }
        return checkedRId
    }
}
