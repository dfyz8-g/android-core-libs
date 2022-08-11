/**
 * Created by Chris Renfrow on 9/26/18.
 */
@file:Suppress("unused")

package com.cren90.android.network

const val TIMEOUT = 60L

//TODO: Pull from common lib once available
const val MILLIS_PER_SECOND = 1000

/*---- 2XX Success--------*/
const val SUCCESS = 200
const val CREATED = 201
const val ACCEPTED = 202
const val NO_CONTENT = 204

/*---- 4XX Client Error----*/
const val BAD_REQUEST = 400
const val UNAUTHORIZED = 401
const val FORBIDDEN = 403
const val NOT_FOUND = 404
const val METHOD_NOT_ALLOWED = 405
const val NOT_ACCEPTABLE = 406
const val REQUEST_TIMEOUT = 408
const val CONFLICT = 409

/*---- 4XX Client Error NonStandard----*/
const val NO_NETWORK = 499


val CLIENT_ERRORS = listOf(
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    METHOD_NOT_ALLOWED,
    NOT_ACCEPTABLE,
    REQUEST_TIMEOUT,
    CONFLICT
)

val VALID_AUTH_ERRORS = listOf(
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND
)

/*---- 5XX Server Error----*/
const val SERVER_ERROR = 500
const val NOT_IMPLEMENTED = 501
const val BAD_GATEWAY = 502
const val SERVICE_UNAVAILABLE = 503
const val GATEWAY_TIMEOUT = 504

val SERVER_ERRORS = listOf(
    SERVER_ERROR,
    NOT_IMPLEMENTED,
    BAD_GATEWAY,
    SERVICE_UNAVAILABLE,
    GATEWAY_TIMEOUT
)

const val AUTHORIZATION_HEADER_KEY = "Authorization"
const val RETRY_COUNT_HEADER = "X-RetryCount"
const val IGNORE_UNAUTHORIZED_HEADER = "X-IgnoreUnauthorized"
const val BEARER_AUTHORIZATION_KEY = "Bearer"

const val NO_INTERNET_CONNECTION_ERROR_CODE = 0