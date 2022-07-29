package com.cren90.android.network.auth

interface TokenValidator {

    fun isTokenValid(token: String?): Boolean
}