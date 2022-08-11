package com.cren90.android.network.logging

import okhttp3.Response

internal fun Response.getLogData(vararg additionalPairs: Pair<String, Any?>): Map<String, Any?> =
    additionalPairs.toMap().toMutableMap().apply {
        put(REQUEST_URL_KEY, this@getLogData.request.url)
        put(HTTP_STATUS_KEY, this@getLogData.code)
    }