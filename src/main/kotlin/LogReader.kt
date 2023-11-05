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

fun readLogsFake(onLogReceived: (log: String) -> Unit) {
    fixedRateTimer(period = 1500, action = {
        onLogReceived("Heyy")
    })
}

object LogReader {
    @JvmStatic
    fun main(args: Array<String>) {
        dummyLogList.forEach { s -> println(buildLogData(s)) }
    }

}

fun getPriority(tag: String): Int {
    return if (tag == "V") Priority.VERBOSE else -1
}

fun buildLogData(textLog: String): LogData {
    var logData = LogData()
    val splitList = textLog.trim().split(" ").filter { s -> s.isNotEmpty() }
    try {
        val dateTime = splitList[0] + " " + splitList[1]
        val pid = splitList[2]
        val tid = splitList[3]
        val priority = getPriority(splitList[4])
        var tag = splitList[5].trim()
        if (tag.last() == ':') {
            tag = tag.substring(0, tag.length - 2)
        }
        val lengthSoFar = "$dateTime$pid$tid$priority$tag     ".length
        val colonIndex = textLog.indexOf(":", lengthSoFar)
        val message = textLog.substring(colonIndex + 1).trim()
        logData = LogData(tid = tid, pid = pid, dateTime = dateTime, tag = tag, priority = priority, message = message)
    } catch (e: Exception) {
        println("Error parsing: $textLog")
        println(e.localizedMessage)
    }
    return logData
}