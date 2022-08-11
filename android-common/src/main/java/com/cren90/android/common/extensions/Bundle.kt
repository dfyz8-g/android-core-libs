@file:Suppress("unused")

package com.cren90.android.common.extensions

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

fun Bundle.getIntOrNull(key: String): Int? = if (!containsKey(key)) null else getInt(key, 0)
fun Bundle.getLongOrNull(key: String): Long? = if (!containsKey(key)) null else getLong(key, 0L)
fun Bundle.getBoolOrNull(key: String): Boolean? = if (!containsKey(key)) null else getBoolean(
    key,
    false
)

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Bundle.getSerializableOrNull(key: String): T? =
    if (!containsKey(key)) null else getSerializable(
        key
    ) as? T

@Suppress("UNCHECKED_CAST")
fun <T : Parcelable> Bundle.getParcelableOrNull(key: String): T? =
    if (!containsKey(key)) null else getParcelable(
        key
    ) as? T
