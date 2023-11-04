import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme.Gray60
import util.sampleLog

@Preview
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray60)
    ) {
        ControlBox()
        LogBox()
    }
}

@Composable
fun LogBox() {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(count = 10) {
                LogItem(sampleLog)
            }
        }
    }
}

@Composable
fun LogItem(text: String) {
    Text(text = text)
}
