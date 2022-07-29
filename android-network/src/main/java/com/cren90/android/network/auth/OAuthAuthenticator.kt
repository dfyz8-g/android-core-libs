/**
 * Created by Chris Renfrow on 2019-12-09.
 */

package com.cren90.android.network.auth

import com.cren90.android.network.AUTHORIZATION_HEADER_KEY
import com.cren90.android.network.BEARER_AUTHORIZATION_KEY
import com.cren90.android.network.IGNORE_UNAUTHORIZED_HEADER
import com.cren90.android.network.RETRY_COUNT_HEADER
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OAuthAuthenticator @Inject constructor(
    private val delegate: OAuthRefreshDelegate,
    private val authRepo: AuthTokenRepo
) :
    Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Timber.i("Authentication error ${response.code} on ${response.request.url}")

        if (response.request.headers[IGNORE_UNAUTHORIZED_HEADER] == "true") {
            Timber.i("Ignoring unauthorized error")
            return null
        }

        if (retryCount(response) > 0) {
            Timber.i("I've already refreshed this request if we're still not authorized we're not going to be.")
            return null
        }

        val accessToken = authRepo.getAccessToken()

        // Only 1 request handle can enter this section at a time to prevent requesting multiple
        // refresh tokens simultaneously (or close to it).
        synchronized(this) {

            val newToken = authRepo.getAccessToken()

            if (hasBearerAuthorizationToken(response)) {
                if (newToken != accessToken && newToken != null) {
                    Timber.i("Looks like we've already re-authenticated, retrying with new token!")
                    return rewriteRequest(response.request, 1, newToken)
                } else {
                    Timber.i("Attempting to re-authenticate...")
                    return reAuthenticate(response.request, 1)
                }
            } else {
                Timber.i("Not a bearer token request. I don't know how to refresh this... I give up!")
                null
            }
        }

        Timber.i("We fell through... this shouldn't have happened but it did...")
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
            Timber.i("Re-authenticate retry count exceeded. Giving up!")
            return null
        }

        delegate.refreshBearerToken()?.let {
            Timber.i("Retrieved new token, re-attempting, object: ${this.hashCode()}")
            delegate.oauthBearerToken?.let { bearer ->
                Timber.i("We have a valid access token, rewrite the request")
                return rewriteRequest(staleRequest, retryCount, bearer)
            }
        }

        Timber.i("Failed to retrieve new token, unable to re-authenticate, object: ${this.hashCode()}")

        return null
    }

    private fun rewriteRequest(staleRequest: Request, retryCount: Int, newToken: String): Request? {
        return staleRequest.newBuilder()
            .header(AUTHORIZATION_HEADER_KEY, "$BEARER_AUTHORIZATION_KEY $newToken")
            .header(RETRY_COUNT_HEADER, retryCount.toString())
            .build()

    }

}