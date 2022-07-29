/**
 * Created by Chris Renfrow on 2/26/21.
 */

package com.cren90.android.network.inject.qualifiers

import javax.inject.Qualifier

// These are used for both OKHTTP and Scarlet

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteAuthenticated

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteUnauthenticated

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalAuthenticated

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalUnauthenticated

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteAuthenticatedSocket

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteUnauthenticatedSocket

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalAuthenticatedSocket

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalUnauthenticatedSocket

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnitTest
