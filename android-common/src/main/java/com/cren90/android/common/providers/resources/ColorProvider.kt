package com.cren90.android.common.providers.resources

import androidx.annotation.ColorRes

interface ColorProvider {

    /**
     * Returns the integer representation of the hex color for the given [resourceID]
     */
    fun getColor(@ColorRes resourceID: Int): Int

    /**
     * Returns the integer representation of the hex color for the given [resourceID], with the given alpha applied
     */
    fun getColor(@ColorRes resourceID: Int, alpha: Float): Int

    /**
     * Returns the integer representation of the hex color at the [proportion] between the [lowColorResourceID] and the [highColorResourceID]
     */
    fun getColor(
        @ColorRes lowColorResourceID: Int,
        @ColorRes highColorResourceID: Int,
        proportion: Float
    ): Int
}