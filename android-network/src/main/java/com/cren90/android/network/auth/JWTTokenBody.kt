/**
 * Created by Chris Renfrow on 11/5/20.
 */

package com.cren90.android.network.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class JWTTokenBody(
    @Json(name = "aud")
    val audience: String? = null,

    @Json(name = "exp")
    val expirationSeconds: Long,

    @Json(name = "iat")
    val issuedAtSeconds: Long,

    @Json(name = "iss")
    val issuer: String? = null,

    @Json(name = "sub")
    val subject: String? = null

) : Serializable