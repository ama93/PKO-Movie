package com.pkomovie.data.remote.interceptors

import android.content.Context
import com.pkomovie.R
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val request: Request = chain.request()
       val newRequest =  request.newBuilder()
            .header(HEADER_AUTHORIZATION, HEADER_AUTHORIZATION_VALUE.format(context.getString(R.string.api_token)))
            .build()
        return chain.proceed(request = newRequest)
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_AUTHORIZATION_VALUE = "Bearer %s"
    }
}