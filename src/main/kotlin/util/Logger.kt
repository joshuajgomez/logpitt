package util

import data.LogData.Priority.INFO
import data.LogData.Priority.VERBOSE
import data.LogData.Priority.DEBUG
import data.LogData.Priority.ERROR
import data.LogData.Priority.WARN

object Logger {

    private const val TAG = "logpitt"

    /**
     * Set to high log levels to avoid printing low priority logs
     */
    private const val ALLOWED_LOG_LEVEL = 0

    private fun logControl(logLevel: Int, message: String) {
        if (logLevel >= ALLOWED_LOG_LEVEL) println("$TAG: $message")
    }

    private val methodName: String
        private get() {
            val element = Thread.currentThread().stackTrace[3]
            var className = element.className
            className = className.substring(className.lastIndexOf(".") + 1)
            val methodName = element.methodName
            return "$className:$methodName> "
        }

    fun entry() {
        logControl(DEBUG, methodName + "entry >>")
    }

    fun exit() {
        logControl(DEBUG, methodName + "exit >>")
    }

    fun info(message: String) {
        logControl(INFO, methodName + message)
    }

    fun debug(message: String) {
        logControl(DEBUG, methodName + message)
    }

    fun verbose(message: String) {
        logControl(VERBOSE, methodName + message)
    }

    fun warn(message: String) {
        logControl(WARN, methodName + message)
    }

    fun error(message: String) {
        logControl(ERROR, methodName + message)
    }
}