package com.mac.loginmvvmsample.view.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.mac.loginmvvmsample.R
import com.mac.loginmvvmsample.base.BaseActivity
import com.mac.loginmvvmsample.databinding.ActivityMainBinding
import com.mac.loginmvvmsample.utils.LoginApplication
import com.mac.loginmvvmsample.viewmodel.DashboardViewModel
import com.mac.loginmvvmsample.viewmodel.LoginViewModel
import com.mac.loginmvvmsample.viewmodel.ViewModelFactory
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {


    @Inject
    lateinit var viewModelProviders: ViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    lateinit var viewModel: DashboardViewModel

    private lateinit var ed : SharedPreferences.Editor


    override val layoutRes: Int
        get() = R.layout.activity_main



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as LoginApplication).getAppComponent().doInjection(this)
        viewModel = ViewModelProviders.of(this, viewModelProviders).get(DashboardViewModel::class.java)
        dataBinding.viewModel = viewModel

        viewModel.checkedRId.observe(this, androidx.lifecycle.Observer { i ->
            if (i==0){
                setLocale("hi")
            }else{
                setLocale("en")
            }
        })

    }


    fun setLocale(lang: String) {
        val myLocale = Locale(lang)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        try {
            res.updateConfiguration(conf, dm)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        ed = sharedPreferences.edit()
        ed.putString("lang", lang)
        ed.apply()

        val mainIntent = Intent(this, LoginActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainIntent)
        finish()    }
}
