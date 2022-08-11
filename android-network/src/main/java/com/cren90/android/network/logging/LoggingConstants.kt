package com.cren90.android.network.logging


// region Data Keys

const val REQUEST_URL_KEY = "requestUrl"
const val HTTP_STATUS_KEY = "httpStatus"
const val RETRY_COUNT_KEY = "retryCount"

// endregion Data Keys

//region Log Messages

const val FIRST_AUTH_FAILED_MESSAGE = "First authentication request was unsuccessful"
const val HEADER_SET_IGNORING_UNAUTHORIZED_MESSAGE =
    "Ignoring unauthorized error due to ignore header."

const val REFRESH_ATTEMPTED_STILL_NOT_AUTHED_MESSAGE =
    "Refresh has been attempted, if we're still not authorized we're not going to be."
const val ALREADY_REAUTHENTICATED_RETRYING_MESSAGE =
    "Already re-authenticated, retrying with new token!"
const val ATTEMPTING_TO_REAUTHENTICATE_MESSAGE = "Attempting to re-authenticate..."
const val NOT_A_BEARER_REQUEST_MESSAGE = "Not a bearer token request. Unable to refresh."
const val FALLTHROUGH_MESSAGE = "We fell through... this shouldn't have happened but it did..."
const val REAUTHENTICATION_RETRY_COUNT_EXCEEDED_MESSAGE =
    "Re-authenticate retry count exceeded. Giving up!"
const val NEW_TOKEN_RETRYING_REQUEST_MESSAGE = "Retrieved new token, retrying request..."
const val VALID_ACCESS_TOKEN_REWRITE_REQUEST_MESSAGE =
    "We have a valid access token, rewrite the request"
const val FAILED_TO_RETRIEVE_NEW_TOKEN_MESSAGE =
    "Failed to retrieve new token, unable to re-authenticate."

//endregion Log Messages