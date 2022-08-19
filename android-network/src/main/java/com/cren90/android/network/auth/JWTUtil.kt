package com.cren90.android.network.auth

import android.util.Base64
import com.squareup.moshi.Moshi
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*

/**
 * Created by Chris Renfrow on 11/5/20.
 */
object JWTUtil: TokenValidator {

    override fun isTokenValid(token: String?): Boolean {
        token?.let { theToken ->
            if (theToken.isEmpty()) return false

            try {
                val split = theToken.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val tokenBody = Moshi.Builder()
                    .build()
                    .adapter(JWTTokenBody::class.java)
                    .fromJson(getJson(split[1]))

                if (tokenBody == null) {
                    return false
                } else {
                    return if (tokenBody.expirationSeconds != 0L) {
                        val calendarNow = Calendar.getInstance()
                        val calendarExpiration = Calendar.getInstance()
                        calendarExpiration.timeInMillis = tokenBody.expirationSeconds * 1000

                        calendarExpiration <= calendarNow
                    } else {
                        //No expiration date set
                        true

                    }

                }

            } catch (e: UnsupportedEncodingException) {
                return false
            }
        }

        return false
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes = Base64.decode(strEncoded, Base64.NO_WRAP)
        return String(decodedBytes, Charset.forName("UTF-8"))
    }
}
