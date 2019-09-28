package com.mac.loginmvvmsample.injection

import android.app.Application
import android.content.Context
import javax.inject.Singleton
import dagger.Module
import dagger.Provides



@Module
internal class AppContext(private val context: Context) {

    @Provides
    @Singleton
    fun contextProvider(): Context {
        return context
    }

}