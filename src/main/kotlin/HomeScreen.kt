import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.LogData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import theme.Gray60

@Preview
@Composable
fun homeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray60)
    ) {
        val logList = remember { mutableStateListOf<LogData>() }
        controlBox(onStartClick = {
            CoroutineScope(Dispatchers.Default).launch {
                readLogsFake { logList.add(it) }
            }
        })
        logBox(logList)
    }
}
