package com.cren90.android.core.logging

import com.cren90.android.core.logging.strategies.LogStrategy

class ModularLogger constructor(vararg logStrategies: LogStrategy): Logger {
    private val _logStrategies = mutableSetOf(*logStrategies)
    fun addLogStrategies(vararg logStrategies: LogStrategy) {
        _logStrategies.addAll(logStrategies)
    }

    fun removeLogStrategies(vararg logStrategies: LogStrategy) {
        _logStrategies.removeAll(logStrategies.toSet())
    }

    private fun createInternalLogger() = ModularLoggerInternal(_logStrategies.toSet())

    override fun withData(vararg data: Pair<String, Any?>): Logger = createInternalLogger().withData(*data)

    override fun withData(data: Map<String, Any?>): Logger = createInternalLogger().withData(data)

    override fun withException(e: Throwable): Logger = createInternalLogger().withException(e)

    override fun fatal(message: String?, tag: String?) = createInternalLogger().fatal(message, tag)

    override fun error(message: String?, tag: String?) = createInternalLogger().error(message, tag)

    override fun warning(message: String?, tag: String?) = createInternalLogger().warning(message, tag)

    override fun info(message: String?, tag: String?) = createInternalLogger().info(message, tag)

    override fun debug(message: String?, tag: String?) = createInternalLogger().debug(message, tag)

    override fun verbose(message: String?, tag: String?) = createInternalLogger().verbose(message, tag)

}