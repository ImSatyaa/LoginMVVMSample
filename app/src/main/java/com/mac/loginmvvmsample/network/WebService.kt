package com.mac.loginmvvmsample.network

import com.google.gson.JsonElement
import com.mac.loginmvvmsample.models.LoginRequestModel
import com.mac.loginmvvmsample.utils.Urls
import com.mac.loginmvvmsample.utils.Urls.LOGIN
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface WebService {

    @POST(LOGIN)
    fun login(@Body loginModel: LoginRequestModel): Observable<JsonElement>
}