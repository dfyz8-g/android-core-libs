@file:Suppress("unused")

package com.cren90.android.core.extensions

val StackTraceElement.simpleClassName: String
    get() = Class.forName(this.className).simpleName