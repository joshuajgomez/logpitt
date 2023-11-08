package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.FilterData
import data.LogData
import kotlinx.coroutines.launch
import theme.*
import util.getSampleFilters
import util.getSampleLogList

@Preview
@Composable
fun previewLogBox() {
    logBox(getSampleLogList, getSampleFilters)
}

@Composable
fun logBox(logList: List<LogData>, filterList: List<FilterData>) {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
//    val filteredList = filterLogs(logList, filterList)
    SelectionContainer {
        LazyColumn(
            state = lazyColumnListState,
            modifier = Modifier
                .padding(all = 3.dp)
                .fillMaxSize()
                .background(color = Gray70, shape = RoundedCornerShape(5.dp))
        ) {
            coroutineScope.launch {
                if (logList.isNotEmpty()) {
                    lazyColumnListState.scrollToItem(logList.lastIndex)
                }
            }
            items(items = logList) {
                logItem(it)
            }
        }
    }
}

fun filterLogs(logList: List<LogData>, filterList: List<FilterData>): List<LogData> {
    if (filterList.isEmpty()) {
        return logList
    }
    val filteredList: ArrayList<LogData> = ArrayList()
    for (logData in logList) {
        for (filterData in filterList) {
            if (logData.log.toLowerCase().contains(filterData.text.toLowerCase())) {
                filteredList.add(logData)
                break
            }
        }
    }
    return filteredList
}

@Composable
fun logItem(logData: LogData) {
    Text(
        text = logData.log,
        color = getColor(logData.priority),
        fontSize = 14.sp,
        fontFamily = courierFont
    )
}

fun getColor(priority: Int) = when (priority) {
    LogData.Priority.ERROR -> Red10
    LogData.Priority.WARN -> Yellow10
    LogData.Priority.INFO -> Green10
    LogData.Priority.DEBUG -> Blue10
    else -> Color.LightGray
}