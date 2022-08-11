/**
 * Created by Chris Renfrow on 2019-11-21.
 */

package com.cren90.android.network.interceptors

import com.cren90.android.logging.Logger
import com.cren90.android.network.BAD_REQUEST
import com.cren90.android.network.events.UpdateEvent
import com.cren90.kotlin.common.events.EventProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CheckAppUpdateInterceptor @Inject constructor(val logger: Logger) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        var responseToReturn = response

        val updateRequired = (response.header("Update-Required")?.toBoolean()) == true
        val updateAvailable = (response.header("Update-Available")?.toBoolean()) == true

        if (updateAvailable || updateRequired) {
            logger
                .withData(
                    "updateRequired" to updateRequired,
                    "updateRecommended" to updateAvailable
                )
                .info("Update is available or required")

            EventProvider.post(UpdateEvent(updateAvailable, updateRequired))

            if (updateRequired) {
                responseToReturn = response.newBuilder()
                    .code(BAD_REQUEST)
                    .build()
            }
        }

        return responseToReturn
    }

}