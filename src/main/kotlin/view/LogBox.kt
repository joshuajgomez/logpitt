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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.LogData
import data.Priority
import kotlinx.coroutines.launch
import theme.*
import util.getSampleLogList

@Preview
@Composable
fun previewLogBox() {
    logBox(getSampleLogList)
}

@Composable
fun logBox(logList: List<LogData>) {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
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

@Composable
fun logItem(logData: LogData) {
    Text(
        text = logData.log,
        color = getColor(logData.priority),
        fontSize = 13.sp,
        modifier = Modifier.padding(bottom = 5.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

fun getColor(priority: Int) = when (priority) {
    Priority.ERROR -> Red10
    Priority.WARN -> Red10
    Priority.INFO -> Green10
    Priority.DEBUG -> Blue10
    else -> Color.LightGray
}