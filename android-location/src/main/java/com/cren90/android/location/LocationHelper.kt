package com.cren90.android.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import javax.inject.Inject

@Suppress("unused")
class LocationHelper @Inject constructor(private val context: Context) {

    val hasLocationPermission: Boolean
        @SuppressLint("ObsoleteSdkInt")
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    val hasLocationEnabled: Boolean
        get() = (context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager)?.let {
            try {
                it.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                it.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (ignored: Exception) {
                false
            }
        } ?: false
}