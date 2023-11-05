package data

class LogData(
    var dateTime: String = "",
    var tid: String = "",
    var pid: String = "",
    var priority: Int = -1,
    var tag: String = "",
    var message: String = ""
) {
    override fun toString() =
        "LogData(dateTime='$dateTime', tid='$tid', pid='$pid', priority=$priority, tag='$tag', message='$message')"
}

object Priority {
    const val VERBOSE = 1
    const val DEBUG = 2
    const val INFO = 4
    const val ASSERT = 6
    const val WARN = 7
    const val ERROR = 8
}