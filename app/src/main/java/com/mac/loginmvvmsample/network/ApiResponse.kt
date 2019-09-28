package com.mac.loginmvvmsample.network

import android.util.Log
import com.google.gson.JsonElement


class ApiResponse private constructor(
    val status: Status,
    val data: JsonElement?,
    val error: Throwable?
) {
    companion object {

        fun loading(): ApiResponse {
            return ApiResponse(Status.LOADING, null, null)
        }

        fun success(data: JsonElement): ApiResponse {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun error(error: Throwable): ApiResponse {
            Log.e("ApiResponse", "error: " + error.message)
            return ApiResponse(Status.ERROR, null, error)
        }
    }

}

