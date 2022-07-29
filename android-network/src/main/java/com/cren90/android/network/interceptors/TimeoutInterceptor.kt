/**
 * Created by Chris Renfrow on 4/8/20.
 */

package com.cren90.android.network.interceptors

import com.cren90.android.network.GATEWAY_TIMEOUT
import com.cren90.android.network.events.TimeoutEvent
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeoutInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = Response.Builder()
            .code(GATEWAY_TIMEOUT)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .message("")
            .body("".toResponseBody(request.body?.contentType()))
            .build()

        try {
            response = chain.proceed(request)

        } catch (e: Throwable) {
            if (e is SocketTimeoutException || e is IOException) {
                EventProvider.post(TimeoutEvent())
            } else {
                Timber.e(e)
                throw e
            }
        }

        return response
    }
}