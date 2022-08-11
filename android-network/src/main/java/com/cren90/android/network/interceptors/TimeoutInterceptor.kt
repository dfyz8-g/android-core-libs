/**
 * Created by Chris Renfrow on 4/8/20.
 */

package com.cren90.android.network.interceptors

import com.cren90.android.logging.Logger
import com.cren90.android.network.REQUEST_TIMEOUT
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeoutInterceptor @Inject constructor(private val logger: Logger) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = Response.Builder()
            .code(REQUEST_TIMEOUT)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .message("")
            .body("".toResponseBody(request.body?.contentType()))
            .build()

        try {
            response = chain.proceed(request)

        } catch (e: Throwable) {
            if (e is SocketTimeoutException || e is IOException) {
                logger
                    .withException(e)
                    .info("Timeout exception occurred returning HTTP Status = $REQUEST_TIMEOUT")
            } else {
                throw e
            }
        }

        return response
    }
}