package com.mac.loginmvvmsample.injection

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mac.loginmvvmsample.network.Repository
import com.mac.loginmvvmsample.network.WebService
import com.mac.loginmvvmsample.utils.Urls.BASE_URL
import com.mac.loginmvvmsample.viewmodel.ViewModelFactory
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import android.preference.PreferenceManager
import android.content.SharedPreferences
import com.mac.loginmvvmsample.BuildConfig


@Module
class UtilModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun getApiCallInterface(retrofit: Retrofit): WebService {
        return retrofit.create(WebService::class.java)
    }

    @Provides
    @Singleton
    fun getRequestHeader(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .build()
                chain.proceed(request)
            }
                .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))

        } else {
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .build()
                chain.proceed(request)
            }

        }

            .connectTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)


        return httpClient.build()
    }

    @Provides
    @Singleton
    fun getRepository(apiCallInterface: WebService): Repository {
        return Repository(apiCallInterface)
    }

    @Provides
    @Singleton
    fun getViewModelFactory(myRepository: Repository): ViewModelProvider.Factory {
        return ViewModelFactory(myRepository)
    }


    // not creating specific class for SP
    @Provides
    @Singleton
    fun providesSharedPreferences(ctx: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

}