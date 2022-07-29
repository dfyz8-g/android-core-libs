package com.cren90.android.core.logging

import com.cren90.android.core.extensions.simpleClassName
import com.cren90.android.core.extensions.stackTraceToString
import com.cren90.android.core.logging.strategies.LogStrategy

class ModularLoggerInternal constructor(private val logStrategies: Set<LogStrategy>): Logger {

    private val data = mutableMapOf<String, Any?>()

    override fun withData(vararg data: Pair<String, Any?>): Logger {
        this.data.putAll(data)
        return this
    }
    override fun withData(data: Map<String, Any?>): Logger {
        this.data.putAll(data)
        return this
    }

    override fun withException(e: Throwable): Logger {
        this.data.put("exception", mapOf(
                "message" to e.message,
                "stacktrace" to e.stackTraceToString()
            )
        )

        return this
    }


    private fun getTag(explicitTag: String?) = explicitTag ?:
                                               Thread.currentThread().stackTrace[5].let { "${it.simpleClassName}.${it.methodName}" }

    override fun fatal(message: String?, tag: String?) {
        logStrategies.forEach {
            it.fatal(message, getTag(tag), data)
        }
    }

    override fun error(message: String?, tag: String?) {
        logStrategies.forEach {
            it.error(message, getTag(tag), data)
        }
    }

    override fun warning(message: String?, tag: String?) {
        logStrategies.forEach {
            it.warning(message, getTag(tag), data)
        }
    }

    override fun info(message: String?, tag: String?) {
        logStrategies.forEach {
            it.info(message, getTag(tag), data)
        }
    }

    override fun debug(message: String?, tag: String?) {
        logStrategies.forEach {
            it.debug(message, getTag(tag), data)
        }
    }

    override fun verbose(message: String?, tag: String?) {
        logStrategies.forEach {
            it.verbose(message, getTag(tag), data)
        }
    }

}