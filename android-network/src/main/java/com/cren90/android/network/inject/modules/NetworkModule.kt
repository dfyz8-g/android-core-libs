/**
 * Created by Chris Renfrow on 2019-11-21.
 */

package com.cren90.android.network.inject.modules

import android.app.Application
import android.content.Context
import com.cren90.android.network.MILLIS_PER_SECOND
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.cren90.android.network.TIMEOUT
import com.cren90.android.network.UuidAdapter
import com.cren90.android.network.auth.OAuthAuthenticator
import com.cren90.android.network.inject.qualifiers.*
import com.cren90.android.network.inject.qualifiers.retrofit.*
import com.cren90.android.network.interceptors.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.zacsweers.moshix.sealed.reflect.MoshiSealedJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [InterceptorModule::class])
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val moshi: Moshi = Moshi.Builder()
        .add(UuidAdapter())
        .add(MoshiSealedJsonAdapterFactory())
        .add(KotlinJsonAdapterFactory())
        .build()

    //----------------------------------------------------------------------------------------------
    // region OKHttp Clients
    //----------------------------------------------------------------------------------------------

    @Provides
    @Singleton
    @RemoteAuthenticated
    fun provideRemoteAuthenticatedOkHttpClient(
        logInterceptor: HttpLoggingInterceptor,
        authorizationHeaderInterceptor: AuthorizationHeaderInterceptor,
        checkAppUpdateInterceptor: CheckAppUpdateInterceptor,
        appVersionHeaderInterceptor: AppVersionHeaderInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        remoteUrlInterceptor: RemoteUrlInterceptor,
        timeoutInterceptor: TimeoutInterceptor,
        oAuthAuthenticator: OAuthAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .authenticator(oAuthAuthenticator)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(remoteUrlInterceptor)
        .addInterceptor(authorizationHeaderInterceptor)
        .addInterceptor(appVersionHeaderInterceptor)
        .addInterceptor(logInterceptor)
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(checkAppUpdateInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()

    @Provides
    @UnitTest
    fun provideUnitTestClient(
        mockInterceptor: BaseMockInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(mockInterceptor)
        .build()

    @Provides
    @Singleton
    @RemoteUnauthenticated
    fun provideRemoteUnauthenticatedClient(
        logInterceptor: HttpLoggingInterceptor,
        remoteUrlInterceptor: RemoteUrlInterceptor,
        checkAppUpdateInterceptor: CheckAppUpdateInterceptor,
        appVersionHeaderInterceptor: AppVersionHeaderInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        timeoutInterceptor: TimeoutInterceptor
    ) = OkHttpClient.Builder()
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(remoteUrlInterceptor)
        .addInterceptor(appVersionHeaderInterceptor)
        .addInterceptor(logInterceptor)
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(checkAppUpdateInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()

    @Provides
    @Singleton
    @LocalAuthenticated
    fun provideLocalAuthenticatedClient(
        logInterceptor: HttpLoggingInterceptor,
        localUrlInterceptor: LocalUrlInterceptor,
        timeoutInterceptor: TimeoutInterceptor,
        oAuthAuthenticator: OAuthAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .authenticator(oAuthAuthenticator)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(localUrlInterceptor)
        .addInterceptor(logInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()

    @Provides
    @Singleton
    @LocalUnauthenticated
    fun provideLocalUnauthenticatedClient(
        logInterceptor: HttpLoggingInterceptor,
        localUrlInterceptor: LocalUrlInterceptor,
        timeoutInterceptor: TimeoutInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(localUrlInterceptor)
        .addInterceptor(logInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()


    @Provides
    @Singleton
    @RemoteAuthenticatedSocket
    fun provideRemoteAuthenticatedSocketOkHttpClient(
        logInterceptor: HttpLoggingInterceptor,
        authorizationHeaderInterceptor: AuthorizationHeaderInterceptor,
        checkAppUpdateInterceptor: CheckAppUpdateInterceptor,
        appVersionHeaderInterceptor: AppVersionHeaderInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        socketUrlInterceptor: SocketUrlInterceptor,
        timeoutInterceptor: TimeoutInterceptor,
        oAuthAuthenticator: OAuthAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .authenticator(oAuthAuthenticator)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(socketUrlInterceptor)
        .addInterceptor(authorizationHeaderInterceptor)
        .addInterceptor(appVersionHeaderInterceptor)
        .addInterceptor(logInterceptor)
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(checkAppUpdateInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()

    @Provides
    @Singleton
    @RemoteUnauthenticatedSocket
    fun provideRemoteUnauthenticatedSocketClient(
        logInterceptor: HttpLoggingInterceptor,
        socketUrlInterceptor: RemoteUrlInterceptor,
        checkAppUpdateInterceptor: CheckAppUpdateInterceptor,
        appVersionHeaderInterceptor: AppVersionHeaderInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        timeoutInterceptor: TimeoutInterceptor
    ) = OkHttpClient.Builder()
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(socketUrlInterceptor)
        .addInterceptor(appVersionHeaderInterceptor)
        .addInterceptor(logInterceptor)
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(checkAppUpdateInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()

    @Provides
    @Singleton
    @LocalAuthenticatedSocket
    fun provideLocalAuthenticatedSocketClient(
        logInterceptor: HttpLoggingInterceptor,
        socketUrlInterceptor: SocketUrlInterceptor,
        timeoutInterceptor: TimeoutInterceptor,
        oAuthAuthenticator: OAuthAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .authenticator(oAuthAuthenticator)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(socketUrlInterceptor)
        .addInterceptor(logInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()

    @Provides
    @Singleton
    @LocalUnauthenticatedSocket
    fun provideLocalUnauthenticatedSocketClient(
        logInterceptor: HttpLoggingInterceptor,
        socketUrlInterceptor: SocketUrlInterceptor,
        timeoutInterceptor: TimeoutInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(socketUrlInterceptor)
        .addInterceptor(logInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()

    //----------------------------------------------------------------------------------------------
    // endregion OKHttp Clients
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // region Retrofit Builders
    //----------------------------------------------------------------------------------------------

    @Provides
    @Singleton
    @RemoteUnauthenticatedNoNulls
    fun provideRemoteUnauthenticatedNoNullsRetrofitBuilder(
        @RemoteUnauthenticated
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))


    @Provides
    @Singleton
    @RemoteUnauthenticatedSerializeNulls
    fun provideRemoteUnauthenticatedSerializeNullsRetrofitBuilder(
        @RemoteUnauthenticated
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(moshi).withNullSerialization()
            )


    @Provides
    @Singleton
    @RemoteAuthenticatedNoNulls
    fun provideRemoteAuthenticatedNoNullsRetrofitBuilder(
        @RemoteAuthenticated
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Provides
    @Singleton
    @RemoteAuthenticatedSerializeNulls
    fun provideRemoteAuthenticatedSerializeNullsRetrofitBuilder(
        @RemoteAuthenticated
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(moshi).withNullSerialization()
            )

    @Provides
    @UnitTestSerializeNulls
    fun provideUnitTestRetrofitBuilder(
        @UnitTest
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(moshi).withNullSerialization()
            )

    @Provides
    @Singleton
    @LocalAuthenticatedNoNulls
    fun provideLocalAuthenticatedNoNullsRetrofitBuilder(
        @LocalAuthenticated
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Provides
    @Singleton
    @LocalAuthenticatedSerializeNulls
    fun provideLocalAuthenticatedSerializeNullsRetrofitBuilder(
        @LocalAuthenticated
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(moshi).withNullSerialization()
            )

    @Provides
    @Singleton
    @LocalUnauthenticatedNoNulls
    fun provideLocalUnauthenticatedNoNullsRetrofitBuilder(
        @LocalUnauthenticated
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Provides
    @Singleton
    @LocalUnauthenticatedSerializeNulls
    fun provideLocalUnauthenticatedSerializeNullsRetrofitBuilder(
        @LocalUnauthenticated
        client: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(moshi).withNullSerialization()
            )

    //----------------------------------------------------------------------------------------------
    // endregion Retrofit Builders
    //----------------------------------------------------------------------------------------------

    @Provides
    @Singleton
    fun provideScarletBuilder(@ApplicationContext context: Context) = Scarlet.Builder()
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
        .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
        .backoffStrategy(
            ExponentialWithJitterBackoffStrategy(
                5L * MILLIS_PER_SECOND,
                5L * MILLIS_PER_SECOND
            )
        )
        .lifecycle(AndroidLifecycle.ofApplicationForeground(context as Application))

    //----------------------------------------------------------------------------------------------
    // region Retrofit Instances
    //----------------------------------------------------------------------------------------------

    @Provides
    @Singleton
    @RemoteUnauthenticatedNoNulls
    fun provideRemoteUnauthenticatedNoNullsRetrofit(
        @RemoteUnauthenticatedNoNulls
        builder: Retrofit.Builder,
        @RemoteBaseUrl remoteUrl: String
    ): Retrofit = builder.baseUrl(remoteUrl).build()

    @Provides
    @Singleton
    @RemoteUnauthenticatedSerializeNulls
    fun provideRemoteUnauthenticatedSerializeNullsRetrofit(
        @RemoteUnauthenticatedSerializeNulls
        builder: Retrofit.Builder,
        @RemoteBaseUrl remoteUrl: String
    ): Retrofit = builder.baseUrl(remoteUrl).build()

    @Provides
    @Singleton
    @RemoteAuthenticatedNoNulls
    fun provideRemoteAuthenticatedNoNullsRetrofit(
        @RemoteAuthenticatedNoNulls
        builder: Retrofit.Builder,
        @RemoteBaseUrl remoteUrl: String
    ): Retrofit =
        builder.baseUrl(remoteUrl).build()

    @Provides
    @Singleton
    @RemoteAuthenticatedSerializeNulls
    fun provideRemoteAuthenticatedSerializeNullsRetrofit(
        @RemoteAuthenticatedSerializeNulls
        builder: Retrofit.Builder,
        @RemoteBaseUrl remoteUrl: String
    ): Retrofit = builder.baseUrl(remoteUrl).build()

    @Provides
    @UnitTestSerializeNulls
    fun provideUnitTestRetrofit(
        @UnitTestSerializeNulls
        builder: Retrofit.Builder
    ): Retrofit =
        builder.baseUrl("").build()

    @Provides
    @Singleton
    @LocalAuthenticatedNoNulls
    fun provideLocalAuthenticatedNoNullsRetrofit(
        @LocalAuthenticatedNoNulls
        builder: Retrofit.Builder,
        @LocalBaseUrl localUrl: String
    ): Retrofit = builder.baseUrl(localUrl).build()

    @Provides
    @Singleton
    @LocalAuthenticatedSerializeNulls
    fun provideLocalAuthenticatedSerializeNullsRetrofit(
        @LocalAuthenticatedSerializeNulls
        builder: Retrofit.Builder,
        @LocalBaseUrl localUrl: String
    ): Retrofit = builder.baseUrl(localUrl).build()

    @Provides
    @Singleton
    @LocalUnauthenticatedNoNulls
    fun provideLocalUnauthenticatedNoNullsRetrofit(
        @LocalUnauthenticatedNoNulls
        builder: Retrofit.Builder,
        @LocalBaseUrl localUrl: String
    ): Retrofit = builder.baseUrl(localUrl).build()

    @Provides
    @Singleton
    @LocalUnauthenticatedSerializeNulls
    fun provideLocalUnauthenticatedSerializeNullsRetrofit(
        @LocalUnauthenticatedSerializeNulls
        builder: Retrofit.Builder,
        @LocalBaseUrl localUrl: String
    ): Retrofit = builder.baseUrl(localUrl).build()

    //----------------------------------------------------------------------------------------------
    // endregion Retrofit Instances
    //----------------------------------------------------------------------------------------------

    @Provides
    @Singleton
    @LocalAuthenticated
    fun provideLocalAuthenticatedScarlet(
        builder: Scarlet.Builder,
        @LocalAuthenticatedSocket client: OkHttpClient,
        @SocketBaseUrl sockeUrl: String

    ) = builder.webSocketFactory(client.newWebSocketFactory(sockeUrl)).build()

    @Provides
    @Singleton
    @LocalUnauthenticated
    fun provideLocalUnauthenticatedScarlet(
        builder: Scarlet.Builder,
        @LocalUnauthenticatedSocket client: OkHttpClient,
        @SocketBaseUrl sockeUrl: String

    ) = builder.webSocketFactory(client.newWebSocketFactory(sockeUrl)).build()

    @Provides
    @Singleton
    @RemoteAuthenticated
    fun provideRemoteAuthenticatedScarlet(
        builder: Scarlet.Builder,
        @RemoteAuthenticatedSocket client: OkHttpClient,
        @SocketBaseUrl sockeUrl: String

    ) = builder.webSocketFactory(client.newWebSocketFactory(sockeUrl)).build()

    @Provides
    @Singleton
    @RemoteUnauthenticated
    fun provideRemoteUnauthenticatedScarlet(
        builder: Scarlet.Builder,
        @RemoteUnauthenticatedSocket client: OkHttpClient,
        @SocketBaseUrl sockeUrl: String

    ) = builder.webSocketFactory(client.newWebSocketFactory(sockeUrl)).build()
}




