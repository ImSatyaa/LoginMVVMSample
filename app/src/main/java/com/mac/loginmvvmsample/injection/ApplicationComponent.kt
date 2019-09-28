package com.mac.loginmvvmsample.injection

import com.mac.loginmvvmsample.view.activity.LoginActivity
import com.mac.loginmvvmsample.view.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppContext::class, UtilModule::class])
@Singleton
interface ApplicationComponent {

    fun doInjection(loginActivity: LoginActivity)
    fun doInjection(dashboard: MainActivity)

}