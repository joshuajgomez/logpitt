import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.concurrent.fixedRateTimer

@Preview
@Composable
fun deskbody() {
    Column {
        val favourites = remember { mutableStateListOf<String>()}
        buttonBody { favourites.add(it) }
        logContainer(favourites.toList())
    }
}

@Composable
fun logContainer(logList: List<String>, filter: String = "") {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val list = logList.filter { log -> filter.isEmpty() || log.contains(filter) }
    LazyColumn (
        state = lazyColumnListState
    ) {
        coroutineScope.launch {
            if(list.isNotEmpty()){
                lazyColumnListState.scrollToItem(list.size-1)
            }
        }
        items(items = list){
            Text(text = it)
        }
    }
}

@Composable
fun buttonBody(onLogReceived:(log: String)->Unit) {
    Button(onClick = {
        CoroutineScope(Dispatchers.Default).launch {
            readLogsFake { onLogReceived(it) }
        }
    }) {
        Text("Start")
    }
}
