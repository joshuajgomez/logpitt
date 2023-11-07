package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import data.LogData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import theme.Gray60
import util.getSampleLogList
import worker.LogReader

@Preview
@Composable
fun homeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray60)
    ) {
        val logList = remember { mutableStateListOf<LogData>() }
        val logReader = LogReader()
        controlBox(
            onStartClick = { isStart ->
                if (isStart) {
                    logReader.setListener(onLogAdded = { logList.add(it) })
                } else {
                    logReader.removeListener()
                }
            },
            onRefreshClick = { logList.clear() })
        logBox(if (isPreview()) getSampleLogList else logList)
    }
}

@Composable
fun isPreview() = LocalInspectionMode.current

