/**
 * Created by Spencer Searle on 2020-02-13.
 */

package com.cren90.android.network.models

import com.squareup.moshi.JsonClass

@Suppress("unused")
@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val message: String?,
    val code: String?,
    val field: String?
)