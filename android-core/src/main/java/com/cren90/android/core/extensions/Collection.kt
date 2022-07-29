@file:Suppress("unused")

package com.cren90.android.core.extensions

infix fun <T> Collection<T>.sameContentAs(collection: Collection<T>?) =
    collection?.let {
        this.size == it.size && this.containsAll(it)
    } ?: false