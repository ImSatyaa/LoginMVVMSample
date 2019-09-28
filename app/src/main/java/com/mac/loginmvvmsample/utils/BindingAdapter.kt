package com.mac.loginmvvmsample.utils

import android.view.View
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


object BindingAdapter {




    @JvmStatic
    @androidx.databinding.BindingAdapter("errorText")
    fun setError(editText: TextView, strOrResId: Any?) {
        if (strOrResId is Int) {
            editText.text = editText.context.getString(strOrResId)
        } else {
            editText.text = strOrResId as? String
        }
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("onFocus")
    fun bindFocusChange(
        editText: TextInputEditText,
        onFocusChangeListener: View.OnFocusChangeListener
    ) {
        if (editText.onFocusChangeListener == null) {
            editText.onFocusChangeListener = onFocusChangeListener
        }
    }


}
