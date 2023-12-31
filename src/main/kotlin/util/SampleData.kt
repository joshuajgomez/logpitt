package util

import data.FilterData
import data.LogData

val getSampleTags: List<String> = listOf(
    "AndroidRuntime",
    "VST_Alexa",
    "Logger",
    ".app #",
)

val getSampleFilters: List<FilterData> = listOf(
    FilterData("AndroidRuntime"),
    FilterData("VST_Alexa"),
    FilterData("Logger"),
    FilterData(".app #"),
)

val getSampleLogList = listOf(
    LogData(
        priority = LogData.Priority.ERROR,
        log = "11-05 20:22:08.608  7152  7874 E MDD     : DownloadProgressMonitor: Can't find file group for uri: android://com.google.android.apps.messaging/files/datadownload/shared/public/datadownloadfile_1699195911784"
    ),
    LogData(
        priority = LogData.Priority.WARN,
        log = "11-05 21:30:00.109   570  1081 W CpuPowerCalculator: UID 1027 CPU freq # mismatch: Power Profile # 2 actual # 8"
    ),
    LogData(
        priority = LogData.Priority.DEBUG,
        log = "11-05 20:25:05.048  2691  2882 D EGL_emulation: app_time_stats: avg=496.43ms min=478.26ms max=512.82ms count=3"
    ),
    LogData(
        priority = LogData.Priority.VERBOSE,
        log = "11-05 20:22:08.354  7152  7961 V cevx    : Is http uri, downloading (uri: https://c.tenor.com/yIGMxqme4k4AAAAk/tenor.gif)"
    ),
    LogData(
        priority = LogData.Priority.ERROR,
        log = "10-21 13:53:57.036     0     0 E         : fail to initialize ptp_kvm"
    ),
    LogData(
        priority = LogData.Priority.INFO,
        log = "10-21 13:53:55.999     0     0 I         : Linux version 6.1.23-android14-4-00257-g7e35917775b8-ab9964412 (build-user@build-host) (Android (9796371, based on r487747) clang version 17.0.0 (https://android.googlesource.com/toolchain/llvm-project d9f89f4d16663d5012e5c09495f3b30ece3d2362), LLD 17.0.0) #1 SMP PREEMPT Mon Apr 17 20:50:58 UTC 2023"
    ),
)