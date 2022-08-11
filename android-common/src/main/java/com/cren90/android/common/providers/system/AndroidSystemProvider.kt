package com.cren90.android.common.providers.system

import android.content.Context
import android.text.format.DateFormat
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("unused")
@Singleton
class AndroidSystemProvider @Inject constructor(val context: Context) : DateFormatProvider {

    override fun is24Hour() = DateFormat.is24HourFormat(context)
}