package com.cren90.android.core.providers.resources

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

interface DrawableProvider {

    /**
     * Returns the drawable value for the given [resourceID] if one exists
     */
    fun getDrawable(@DrawableRes resourceID: Int?): Drawable?

}