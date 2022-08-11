/**
 * Created by Chris Renfrow on 6/25/20.
 */

package com.cren90.android.network.interceptors

import com.cren90.android.network.BEARER_AUTHORIZATION_KEY
import com.cren90.android.network.auth.AuthTokenRepo
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationHeaderInterceptor @Inject constructor(
    private val authTokenRepo: AuthTokenRepo
) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "$BEARER_AUTHORIZATION_KEY ${authTokenRepo.getAccessToken()}"
                )
                .build()
        )
    }
}