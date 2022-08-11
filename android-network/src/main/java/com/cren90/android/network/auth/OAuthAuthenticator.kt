/**
 * Created by Chris Renfrow on 2019-12-09.
 */

package com.cren90.android.network.auth

import com.cren90.android.logging.Logger
import com.cren90.android.network.*
import com.cren90.android.network.logging.*
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OAuthAuthenticator @Inject constructor(
    private val delegate: OAuthRefreshDelegate,
    private val authRepo: AuthTokenRepo,
    private val logger: Logger
) :
    Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val requestData = response.getLogData(RETRY_COUNT_KEY to retryCount(response))


        if (response.code in VALID_AUTH_ERRORS) {
            logger.withData(requestData).info(FIRST_AUTH_FAILED_MESSAGE)
        } else {
            logger.withData(requestData).warning(FIRST_AUTH_FAILED_MESSAGE)
        }

        if (response.request.headers[IGNORE_UNAUTHORIZED_HEADER] == "true") {

            logger.withData(requestData).info(HEADER_SET_IGNORING_UNAUTHORIZED_MESSAGE)
            return null
        }

        if (retryCount(response) > 0) {
            logger.withData(requestData).info(REFRESH_ATTEMPTED_STILL_NOT_AUTHED_MESSAGE)
            return null
        }

        val accessToken = authRepo.getAccessToken()

        // Only 1 request handle can enter this section at a time to prevent requesting multiple
        // refresh tokens simultaneously (or close to it).
        synchronized(this) {

            val newToken = authRepo.getAccessToken()

            if (hasBearerAuthorizationToken(response)) {
                if (newToken != accessToken && newToken != null) {
                    logger.withData(requestData).debug(ALREADY_REAUTHENTICATED_RETRYING_MESSAGE)
                    return rewriteRequest(response.request, 1, newToken)
                } else {
                    logger.withData(requestData).debug(ATTEMPTING_TO_REAUTHENTICATE_MESSAGE)
                    return reAuthenticate(response.request, 1)
                }
            } else {
                logger.withData(requestData).warning(NOT_A_BEARER_REQUEST_MESSAGE)
                null
            }
        }

        logger.withData(requestData).error(FALLTHROUGH_MESSAGE)
        return null
    }

    private fun hasBearerAuthorizationToken(response: Response): Boolean {
        return response.request.header(AUTHORIZATION_HEADER_KEY)
            ?.startsWith(BEARER_AUTHORIZATION_KEY) == true
    }

    private fun retryCount(response: Response): Int {
        return response.request.header(RETRY_COUNT_HEADER)?.toInt() ?: 0
    }

    private fun reAuthenticate(staleRequest: Request, retryCount: Int): Request? {
        if (retryCount > 1) {
            logger.withData("retryCount" to retryCount)
                .info(REAUTHENTICATION_RETRY_COUNT_EXCEEDED_MESSAGE)
            return null
        }

        delegate.refreshBearerToken()?.let {
            logger.withData().info(NEW_TOKEN_RETRYING_REQUEST_MESSAGE)
            delegate.oauthBearerToken?.let { bearer ->
                logger.info(VALID_ACCESS_TOKEN_REWRITE_REQUEST_MESSAGE)
                return rewriteRequest(staleRequest, retryCount, bearer)
            }
        }

        logger.info(FAILED_TO_RETRIEVE_NEW_TOKEN_MESSAGE)

        return null
    }

    private fun rewriteRequest(staleRequest: Request, retryCount: Int, newToken: String): Request {
        return staleRequest.newBuilder()
            .header(AUTHORIZATION_HEADER_KEY, "$BEARER_AUTHORIZATION_KEY $newToken")
            .header(RETRY_COUNT_HEADER, retryCount.toString())
            .build()

    }

}