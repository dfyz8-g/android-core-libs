/**
 * Created by Chris Renfrow on 2019-11-21.
 */

package com.cren90.android.network.interceptors

import com.cren90.android.network.BAD_REQUEST
import com.cren90.android.network.events.UpdateEvent
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CheckAppUpdateInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        var responseToReturn = response

        val updateRequired = (response.header("Update-Required")?.toBoolean()) == true
        val updateRecommended = (response.header("Update-Recommended")?.toBoolean()) == true

        if (updateRecommended || updateRequired) {
            EventProvider.post(UpdateEvent(updateRecommended, updateRequired))

            if (updateRequired) {
                responseToReturn = response.newBuilder()
                    .code(BAD_REQUEST)
                    .build()
            }
        }

        return responseToReturn
    }

}