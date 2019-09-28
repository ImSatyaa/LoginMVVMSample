package com.mac.loginmvvmsample.models

import com.google.gson.annotations.SerializedName

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.mac.loginmvvmsample.R

class LoginRequestModel : BaseObservable() {

    var userIdError = ObservableField<Int>()
    var passwordError = ObservableField<Int>()

    @SerializedName("email")
    var userId: String? = ""
    var password: String? = ""


    val isIdValid: Boolean
        @Bindable
        get() = checkIdValid(false)

    val isPassValid: Boolean
        @Bindable
        get() = checkPassValid(false)


    fun checkIdValid(setMsg: Boolean): Boolean {
        if (this.userId != null && this.userId!!.isNotEmpty()) {

            val num = userId
            return if (num.toString().length > 3) {
                userIdError.set(null)
                true
            } else {
                if (setMsg)
                    userIdError.set(R.string.invalid_email)
                false
            }

        } else {
            if (setMsg) {
                userIdError.set(R.string.invalid_email)
            }
        }
        return false
    }


    fun checkPassValid(setMsg: Boolean): Boolean {

        if (this.password != null && this.password!!.isNotEmpty()) {

            return if (this.password!!.length >= 6) {
                passwordError.set(null)
                true
            } else {
                if (setMsg)
                    passwordError.set(R.string.invalid_pass)
                false

            }
        } else {
            if (setMsg) {
                passwordError.set(R.string.empty_password)
            }
        }
        return false
    }


}
