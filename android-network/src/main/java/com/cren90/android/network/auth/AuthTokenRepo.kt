/**
 * Created by Chris Renfrow on 6/25/20.
 */

package com.cren90.android.network.auth

import androidx.lifecycle.LiveData

interface AuthTokenRepo {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    suspend fun refreshAccessToken(): String?
    suspend fun logout()
    suspend fun clearTokens()
    val refreshToken: LiveData<String>
    val accessToken: LiveData<String>
}