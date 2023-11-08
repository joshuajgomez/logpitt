package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.platform.LocalInspectionMode
import data.FilterData
import data.LogData
import theme.Gray60
import util.getSampleFilters
import util.getSampleLogList
import worker.LogReader
import java.awt.FileDialog
import java.awt.Window

@Preview
@Composable
fun homeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray60)
    ) {

        val logList = remember { mutableStateListOf<LogData>() }
        val filterList = remember { mutableStateListOf<FilterData>() }
        val logReader = LogReader()

        controlBox(
            filterList = filterList,
            onStartClick = { isStart ->
                if (isStart) {
                    logReader.readLogs()
                    logReader.setListener(onLogAdded = { logList.add(it) })
                } else {
                    logReader.removeListener()
                }
            },
            onRefreshClick = { logList.clear() },
            onFilterAdded = { filterList.add(FilterData(it)) },
            onFilterRemoved = { filterList.remove(it) },
            onFileUploadClick = {
                browseFile { it ->
                    logReader.readLogs(it)
                    logReader.setListener(onLogAdded = { logList.add(it) })
                }
            },
        )

        logBox(
            logList = if (isPreview()) getSampleLogList else logList,
            filterList = if (isPreview()) getSampleFilters else filterList,
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
