package com.mac.loginmvvmsample.utils

import android.app.Application
import android.content.Context
import com.mac.loginmvvmsample.injection.*
import com.mac.loginmvvmsample.injection.AppContext

class LoginApplication :Application() {

    private var appComponents: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponents = DaggerApplicationComponent.builder()
            .appContext(AppContext(this))
            .utilModule(UtilModule())
            .build()
    }

    fun getAppComponent(): ApplicationComponent {
        return appComponents!!
    }




}