import data.LogData
import data.Priority
import util.dummyLogList
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.concurrent.fixedRateTimer

fun readLogs(onLogAdded: (log: LogData) -> Unit) {
    try {
        val process = Runtime.getRuntime().exec("adb logcat")
        val bufferedReader = BufferedReader(
            InputStreamReader(process.inputStream)
        )
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            if (!line.isNullOrBlank()) {
                onLogAdded(buildLogData(line!!))
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun readLogsFake(onLogAdded: (log: LogData) -> Unit) {
    fixedRateTimer(period = 500, action = {
        val log = dummyLogList.random().log
        onLogAdded(buildLogData(log))
    })
}

object LogReader {
    @JvmStatic
    fun main(args: Array<String>) {
        dummyLogList.forEach { s -> buildLogData(s.log) }
    }

}

fun getPriority(priority: String) = when(priority){
    "E" -> Priority.ERROR
    "W" -> Priority.WARN
    "I" -> Priority.INFO
    "A" -> Priority.ASSERT
    "D" -> Priority.DEBUG
    "V" -> Priority.VERBOSE
    else -> Priority.DEBUG
}

const val BEGIN_LOG_PREFIX = "---------"

fun buildLogData(textLog: String): LogData {
    if (textLog.startsWith(BEGIN_LOG_PREFIX)) {
        return LogData(
            priority = Priority.SYSTEM,
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
            tag = if(tag.length == 1) "" else tag.substring(0, tag.length - 2)
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