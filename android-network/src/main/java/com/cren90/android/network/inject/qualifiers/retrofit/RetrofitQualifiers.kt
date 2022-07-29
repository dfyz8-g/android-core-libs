/**
 * Created by Chris Renfrow on 2/26/21.
 */

package com.cren90.android.network.inject.qualifiers.retrofit

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalAuthenticatedNoNulls

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalAuthenticatedSerializeNulls

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalUnauthenticatedNoNulls

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalUnauthenticatedSerializeNulls

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteAuthenticatedNoNulls

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteAuthenticatedSerializeNulls

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteUnauthenticatedNoNulls

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteUnauthenticatedSerializeNulls

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnitTestSerializeNulls


