@file:Suppress("unused")

package com.cren90.android.common.extensions

import android.content.Context
import android.text.Spanned
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.cren90.kotlin.common.extensions.toHtmlSpan

fun Context.getHtmlSpannedString(@StringRes id: Int): Spanned = getString(id).toHtmlSpan()

fun Context.getHtmlSpannedString(@StringRes id: Int, vararg formatArgs: Any): Spanned = getString(
    id,
    *formatArgs
).toHtmlSpan()

fun Context.getQuantityHtmlSpannedString(@PluralsRes id: Int, quantity: Int): Spanned =
    resources.getQuantityString(
        id,
        quantity
    ).toHtmlSpan()

fun Context.getQuantityHtmlSpannedString(
    @PluralsRes id: Int, quantity: Int,
    vararg formatArgs: Any
): Spanned = resources.getQuantityString(
    id,
    quantity,
    *formatArgs
).toHtmlSpan()