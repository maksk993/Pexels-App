package com.maksk993.data.models.okhttp

import com.google.gson.Gson
import okhttp3.OkHttpClient

object Client {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", API_KEY)
                .build()
            it.proceed(request)
        }
        .build()

    val gson = Gson()
}