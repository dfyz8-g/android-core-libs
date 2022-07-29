@file:Suppress("unused")

package com.cren90.android.core.extensions

fun <K, V> MutableMap<K, V>.putAll(from: Map<K, V>?) {
    from?.let {
        putAll(it)
    }
}