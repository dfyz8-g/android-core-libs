package com.cren90.android.common.providers.resources

import androidx.annotation.DimenRes

interface DimenProvider {

    /**
     * Returns the dimension value for [resourceID]
     */
    fun getDimen(@DimenRes resourceID: Int): Float
}