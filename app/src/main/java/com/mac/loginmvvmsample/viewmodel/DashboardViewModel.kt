package com.mac.loginmvvmsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mac.loginmvvmsample.network.Repository

class DashboardViewModel (private val repository: Repository) : ViewModel() {

    val checkedRId = MutableLiveData<Int>()

    fun onChangeLangClick(): MutableLiveData<Int> {
        if (checkedRId.value ==null || checkedRId.value == 1){
            checkedRId.value = 0
        }else{
            checkedRId.value = 1
        }
        return checkedRId
    }
}