package com.cren90.android.core.providers.system

import android.content.Context
import android.text.format.DateFormat
import com.cren90.android.core.providers.system.DateFormatProvider
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("unused")
@Singleton
class AndroidSystemProvider @Inject constructor(val context: Context) : DateFormatProvider {

    override fun is24Hour() = DateFormat.is24HourFormat(context)
}