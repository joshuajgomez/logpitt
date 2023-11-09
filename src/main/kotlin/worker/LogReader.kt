package worker

import data.LogData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.Logger
import util.getSampleLogList
import java.io.*
import kotlin.concurrent.fixedRateTimer

class LogReader {

    private var mOnLogAdded: (logData: LogData) -> Unit = {}

    private var mInputStream: InputStream? = null

    private var mProcess: Process? = null

    private object LogReader {
        @JvmStatic
        fun main(args: Array<String>) {
            Logger.entry()
            getSampleLogList.forEach { s -> LogReader().buildLogData(s.log) }
        }
    }

    fun removeListener() {
        Logger.entry()
        this.mOnLogAdded = {}
    }

    fun readLogs(filePath: String = "", onLogAdded: (logData: LogData) -> Unit) {
        Logger.entry()
        mOnLogAdded = onLogAdded
        CoroutineScope(Dispatchers.Default).launch {
            if (filePath.isEmpty()) {
                readAdbLogs()
            } else {
                readLogsFromFile(filePath)
            }
        }
    }

    private fun readLogsFromFile(filePath: String) {
        Logger.entry()
        val inputStream = File(filePath).inputStream()
        inputStream.bufferedReader()
            .forEachLine {
                if (it.isNotEmpty())
                    mOnLogAdded(buildLogData(it))
                Logger.info(it)
            }
    }

    private fun readAdbLogs() {
        Logger.entry()
        val process = Runtime.getRuntime().exec("adb logcat")
        val inputStream = process.inputStream
        inputStream.bufferedReader().forEachLine {
            if (it.isNotEmpty())
                mOnLogAdded(buildLogData(it))
        }
    }

    private fun readLogsFake() {
        Logger.entry()
        fixedRateTimer(period = 500, action = {
            val log = getSampleLogList.random().log
            mOnLogAdded(buildLogData(log))
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
            Logger.error("Error parsing: " + e.message + " " + textLog)
        }
        return logData
    }
}
