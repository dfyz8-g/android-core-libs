@file:Suppress("unused")

package com.cren90.android.core.extensions

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*

fun UUID.toByteArray(): ByteArray = ByteBuffer.allocate(Long.SIZE_BYTES * 2).apply {
    order(ByteOrder.BIG_ENDIAN)
    putLong(mostSignificantBits)
    putLong(leastSignificantBits)
}.array()