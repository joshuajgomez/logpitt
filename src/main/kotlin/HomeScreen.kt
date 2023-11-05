import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.LogData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import theme.Gray60

@Preview
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray60)
    ) {
        val logList = remember { mutableStateListOf<LogData>() }
        ControlBox(onStartClick = {
            CoroutineScope(Dispatchers.Default).launch {
                readLogs { logList.add(it) }
            }
        })
        LogBox(logList)
    }
}

fun startReadingLogs() {
    CoroutineScope(Dispatchers.Default).launch {
        readLogs { onLogReceived(it) }
    }
}

fun onLogReceived(it: LogData) {
    TODO("Not yet implemented")
}

@Composable
fun LogBox(logList: List<LogData>) {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            coroutineScope.launch {
                if (logList.isNotEmpty()) {
                    lazyColumnListState.scrollToItem(logList.lastIndex)
                }
            }
            items(items = logList) {
                LogItem(it)
            }
        }
    }
}

@Composable
fun LogItem(text: LogData) {
    Text(text = text.toString())
}
