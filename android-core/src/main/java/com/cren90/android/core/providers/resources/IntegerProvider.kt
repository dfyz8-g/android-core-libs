package com.cren90.android.core.providers.resources

import androidx.annotation.IntegerRes

interface IntegerProvider {

    /**
     * Returns the integer value for the given [resourceID]
     */
    fun getInt(@IntegerRes resourceID: Int): Int
}