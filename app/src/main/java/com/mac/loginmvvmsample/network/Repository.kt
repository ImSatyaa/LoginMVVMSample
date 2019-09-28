package com.mac.loginmvvmsample.network

import com.google.gson.JsonElement
import com.mac.loginmvvmsample.models.LoginRequestModel
import io.reactivex.Observable

class Repository(private val apiCallInterface: WebService) {

    /*
     * method to call login
     * */

    fun executeLogin(loginModel: LoginRequestModel): Observable<JsonElement> {
        return apiCallInterface.login(loginModel)
    }



}
