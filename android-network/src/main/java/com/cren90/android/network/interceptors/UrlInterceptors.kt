/**
 * Created by Chris Renfrow on 8/25/21.
 */

package com.cren90.android.network.interceptors

import com.cren90.android.network.BaseUrlProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteUrlInterceptor @Inject constructor(private val urlProvider: BaseUrlProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val oldUrl = oldRequest.url.toString()
        val newUrl = oldUrl.replace(urlProvider.getDefaultRemoteUrl(), urlProvider.getRemoteUrl())
        val newRequest = oldRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}

@Singleton
class LocalUrlInterceptor @Inject constructor(private val urlProvider: BaseUrlProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val oldUrl = oldRequest.url.toString()
        val newUrl = oldUrl.replace(urlProvider.getDefaultRemoteUrl(), urlProvider.getRemoteUrl())
        val newRequest = oldRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}

@Singleton
class SocketUrlInterceptor @Inject constructor(private val urlProvider: BaseUrlProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val oldUrl = oldRequest.url.toString()
        val newUrl = oldUrl.replace(urlProvider.getDefaultRemoteUrl(), urlProvider.getRemoteUrl())
        val newRequest = oldRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}