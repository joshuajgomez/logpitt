package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.FilterData
import data.LogData
import kotlinx.coroutines.launch
import theme.*
import util.getSampleLogList

@Preview
@Composable
fun previewLogBox() {
    logBox()
}

@Composable
fun logBox(
    logList: List<LogData> = getSampleLogList,
    filterList: List<FilterData> = listOf(),
    lazyColumnListState: LazyListState = LazyListState()
) {
    val coroutineScope = rememberCoroutineScope()
    SelectionContainer {
        LazyColumn(
            state = lazyColumnListState,
            modifier = Modifier.padding(all = 3.dp).fillMaxSize()
                .background(color = Gray70, shape = RoundedCornerShape(5.dp))
        ) {
            coroutineScope.launch {
                if (logList.isNotEmpty()) {
                    if (!lazyColumnListState.isScrollInProgress)
                        lazyColumnListState.scrollToItem(logList.lastIndex)
                }
            }
            items(items = logList) {
                if (filterList.isEmpty() || isInFilter(it.log, filterList))
                    logItem(it)
            }
        }
    }
}

fun isInFilter(log: String, filterList: List<FilterData>): Boolean {
    for (filterData in filterList)
        if (log.lowercase().contains(filterData.text.lowercase())) return true
    return false
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