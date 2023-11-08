package worker

import data.LogData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.getSampleLogList
import java.io.*
import kotlin.concurrent.fixedRateTimer

class LogReader {

    private var onLogAdded: (logData: LogData) -> Unit = {}

    private object LogReader {
        @JvmStatic
        fun main(args: Array<String>) {
            getSampleLogList.forEach { s -> LogReader().buildLogData(s.log) }
        }
    }

    fun setListener(onLogAdded: (logData: LogData) -> Unit) {
        this.onLogAdded = onLogAdded
    }

    fun removeListener() {
        this.onLogAdded = {}
    }

    fun readLogs(filePath: String = "") {
        CoroutineScope(Dispatchers.Default).launch {
            if (filePath.isEmpty()) {
                readAdbLogs()
            } else {
                readLogsFromFile(filePath)
            }
        }
    }

    private fun readLogsFromFile(filePath: String) {
        val inputStream: InputStream = File(filePath).inputStream()
        readInputStream(inputStream)
    }

    private fun readAdbLogs() {
        val process = Runtime.getRuntime().exec("adb logcat")
        readInputStream(process.inputStream)
    }


    private fun readInputStream(inputStream: InputStream) {
        inputStream.bufferedReader().forEachLine { if (it.isNotEmpty()) onLogAdded(buildLogData(it)) }
    }

    private fun readLogsFake() {
        fixedRateTimer(period = 500, action = {
            val log = getSampleLogList.random().log
            onLogAdded(buildLogData(log))
        })
    }

    private fun getPriority(priority: String) = when (priority) {
        "E" -> LogData.Priority.ERROR
        "W" -> LogData.Priority.WARN
        "I" -> LogData.Priority.INFO
        "A" -> LogData.Priority.ASSERT
        "D" -> LogData.Priority.DEBUG
        "V" -> LogData.Priority.VERBOSE
        else -> LogData.Priority.DEBUG
    }

    private val BEGIN_LOG_PREFIX = "---------"

    private fun buildLogData(textLog: String): LogData {
        if (textLog.startsWith(BEGIN_LOG_PREFIX)) {
            return LogData(
                priority = LogData.Priority.SYSTEM,
                log = textLog
            )
        }
        var logData = LogData()
        val splitList = textLog.trim().split(" ").filter { s -> s.isNotEmpty() }
        try {
            val dateTime = splitList[0] + " " + splitList[1]
            val pid = splitList[2]
            val tid = splitList[3]
            val priority = getPriority(splitList[4])
            var tag = splitList[5].trim()
            if (tag.last() == ':') {
                tag = if (tag.length == 1) "" else tag.substring(0, tag.length - 2)
            }
            val lengthSoFar = "$dateTime$pid$tid$priority$tag     ".length
            val colonIndex = textLog.indexOf(":", lengthSoFar)
            val message = textLog.substring(colonIndex + 1).trim()
            logData = LogData(
                tid = tid,
                pid = pid,
                dateTime = dateTime,
                tag = tag,
                priority = priority,
                message = message,
                log = textLog
            )
        } catch (e: Exception) {
            println("Error parsing #${e.stackTrace.first().lineNumber}: $textLog")
            println(splitList)
            e.printStackTrace()
        }
        return logData
    }
}
