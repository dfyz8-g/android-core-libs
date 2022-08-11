/**
 * Created by Chris Renfrow on 2019-11-21.
 */

package com.cren90.android.network.interceptors

import android.content.Context
import android.content.pm.PackageManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppVersionHeaderInterceptor @Inject constructor(private val context: Context) : Interceptor {
    @Override
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("X-AppVersion", getAppVersionHeader())
                .addHeader("X-AppBundle", "android-" + context.packageName)
                .addHeader("Content-Type", "application/json")
                .build()
        )
    }

    private fun getAppVersionHeader(): String {
        return try {
            "android-" + context.packageManager.getPackageInfo(
                context.packageName,
                0
            ).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "android"
        }
    }
}