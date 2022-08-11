package com.cren90.android.network.utilities

/*import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.haroldadmin.cnradapter.NetworkResponse
import timber.log.Timber*/

/*
object ApiUtil {
    suspend fun <T : Any, U : Any> handleAPIResponse(
        apiCall: NetworkResponse<T, U>,
        onSuccess: suspend (responseObject: T) -> T?,
        onServerError: suspend (errorObject: U?, code: Int) -> Unit,
        onError: suspend (t: Throwable) -> Unit
    ): T? {
        try {
            when (apiCall) {
                is NetworkResponse.Success -> return onSuccess(apiCall.body)
                is NetworkResponse.ServerError -> onServerError(apiCall.body, apiCall.code)
                is NetworkResponse.NetworkError -> onError(apiCall.error)
                is NetworkResponse.UnknownError -> {
                    Timber.e("API Error occurred: ${apiCall}")
                    FirebaseCrashlytics.getInstance()
                        .recordException(Exception("API Error: ${apiCall.error}"))
                    onError(apiCall.error)
                }
            }
        } catch (t: Throwable) {
            onError(t)
        }
        return null
    }

    fun <T : Any, U : Any> handleAPIResponse(
        apiCall: () -> NetworkResponse<T, U>,
        onSuccess: (responseObject: T) -> Unit,
        onServerError: (errorObject: U?) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        try {
            when (val response = apiCall.invoke()) {
                is NetworkResponse.Success -> onSuccess(response.body)
                is NetworkResponse.ServerError -> onServerError(response.body)
                is NetworkResponse.NetworkError -> onError(response.error)
                is NetworkResponse.UnknownError -> TODO()
            }
        } catch (t: Throwable) {
            onError(t)
        }
    }
}*/
