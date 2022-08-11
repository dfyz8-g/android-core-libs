/**
 * Created by Chris Renfrow on 8/25/21.
 */

package com.cren90.android.network

interface BaseUrlProvider {
    fun getRemoteUrl(): String
    fun getLocalUrl(): String
    fun getSocketUrl(): String

    fun getDefaultRemoteUrl(): String
    fun getDefaultLocalUrl(): String
    fun getDefaultSocketUrl(): String
}