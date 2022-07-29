package com.cren90.android.core.providers.resources

import androidx.annotation.BoolRes

interface BooleanProvider {
    /**
     * Returns the boolean value for the given [resourceID]
     */
    fun getBoolean(@BoolRes resourceID: Int): Boolean
}