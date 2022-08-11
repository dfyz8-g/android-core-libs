/**
 * Created by Chris Renfrow on 2/26/21.
 */

package com.cren90.android.network.inject.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SocketBaseUrl



