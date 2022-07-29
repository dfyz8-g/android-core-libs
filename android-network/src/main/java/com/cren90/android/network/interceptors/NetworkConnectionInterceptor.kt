/**
 * Created by Chris Renfrow on 2019-12-06.
 */

package com.cren90.android.network.interceptors

import android.content.Context
import com.cren90.android.network.NO_INTERNET_CONNECTION_ERROR_CODE
import com.cren90.android.network.REQUEST_TIMEOUT
import com.cren90.android.network.events.NoNetworkEvent
import com.smartrent.network.utilities.NetworkConnectionUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectionInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        return if (NetworkConnectionUtility.isNetworkConnected(context)) {
            chain.proceed(request)
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                EventProvider.post(NoNetworkEvent())
            }

            val body =
                "\"{ \\\"code\\\": $NO_INTERNET_CONNECTION_ERROR_CODE, \\\"description\\\": \\\"No internet connection\\\" }\""
            Response.Builder()
                .code(REQUEST_TIMEOUT)
                .protocol(Protocol.HTTP_2)
                .message("No internet connection")
                .body(body.toResponseBody(body.toMediaTypeOrNull()))
                .request(request)
                .build()
        }
    }
}