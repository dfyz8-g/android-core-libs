package com.cren90.android.common.providers.resources

import android.text.Spanned
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface StringProvider {

    /**
     * Returns the string value for the given [resourceID]
     */
    fun getString(@StringRes resourceID: Int): String

    /**
     * Returns the string value for the given [resourceID] with the specified [formatArgs]
     */
    fun getString(@StringRes resourceID: Int, vararg formatArgs: Any): String

    /**
     * Returns the plural string value for the given [resourceID] and [count]
     */
    fun getPluralString(@PluralsRes resourceID: Int, count: Int): String

    /**
     * Returns the plural string value for the given [resourceID] and [count] with the specified [formatArgs]
     */
    fun getPluralString(@PluralsRes resourceID: Int, count: Int, vararg formatArgs: Any): String

    /**
     * Returns an HTML spanned string for the given [resourceID]
     */
    fun getHtmlString(resourceID: Int): Spanned

    /**
     * Returns an HTML spanned string for the given [resourceID] with the specified [formatArgs]
     */
    fun getHtmlString(resourceID: Int, vararg formatArgs: Any): Spanned
}