package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.platform.LocalInspectionMode
import data.FilterData
import data.LogData
import kotlinx.coroutines.launch
import theme.Gray60
import util.getSampleFilters
import util.getSampleLogList
import worker.LogReader
import java.awt.FileDialog

@Preview
@Composable
fun homeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray60)
    ) {

        val isPaused = remember { mutableStateOf(true) }
        val logList = remember { mutableStateListOf<LogData>() }
        val filterList = remember { mutableStateListOf<FilterData>() }
        val logReader = LogReader()

        val lazyColumnListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        controlBox(
            isPaused = isPaused.value,
            filterList = filterList,
            onStartClick = {
                if (isPaused.value) {
                    isPaused.value = false
                    logReader.readLogs { logList.add(it) }
                } else {
                    isPaused.value = true
                }
            },
            onRefreshClick = { logList.clear() },
            onFilterAdded = { filterList.add(FilterData(it)) },
            onFilterRemoved = { filterList.remove(it) },
            onFileUploadClick = {
                browseFile { it ->
                    logReader.readLogs(filePath = it) { logList.add(it) }
                }
            },
            onGoDownClick = {
                coroutineScope.launch {
                    lazyColumnListState.scrollToItem(logList.lastIndex)
                }
            }
        )

        logBox(
            logList = if (isPreview()) getSampleLogList else logList,
            filterList = if (isPreview()) getSampleFilters else filterList,
            lazyColumnListState = lazyColumnListState,
        )
    }
}

@Composable
fun isPreview() = LocalInspectionMode.current

fun browseFile(readFile: (filePath: String) -> Unit) {
    val fileDialog = FileDialog(ComposeWindow())
    fileDialog.isVisible = true
    val filePath = fileDialog.directory + fileDialog.file
    if (filePath.isNotEmpty() && filePath != "nullnull") readFile(filePath)
}
