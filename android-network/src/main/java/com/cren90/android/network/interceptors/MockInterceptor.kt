/**
 * Created by Chris Renfrow on 2019-11-21.
 */

package com.cren90.android.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Suppress("unused")
class MockInterceptor @Inject constructor() : BaseMockInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}

interface BaseMockInterceptor : Interceptor
