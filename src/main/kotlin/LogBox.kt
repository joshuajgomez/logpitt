import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import data.LogData
import data.Priority
import kotlinx.coroutines.launch
import theme.Blue10
import theme.Gray60
import theme.Green10
import theme.Red10

@Composable
fun logBox(logList: List<LogData>) {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize().background(color = Gray60)) {
        LazyColumn(state = lazyColumnListState, modifier = Modifier.background(color = Gray60)) {
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
        color = getColor(logData.priority)
    )
}

fun getColor(priority: Int) = when (priority) {
    Priority.ERROR -> Red10
    Priority.WARN -> Red10
    Priority.INFO -> Green10
    Priority.DEBUG -> Blue10
    else -> Color.LightGray
}