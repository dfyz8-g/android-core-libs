@file:Suppress("unused")

package com.cren90.android.core.extensions

import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.stackTraceToString(): String = StringWriter().use { stringWriter ->
    PrintWriter(stringWriter).use { printWriter ->
        this.printStackTrace(printWriter)
        stringWriter.toString()
    }
}