import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Gray60

@Preview
@Composable
fun ControlBox(onStartClick: () -> Unit) {
    val tags = remember { mutableStateListOf<String>() }
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(color = Gray60)
            .padding(all = 10.dp)
    ) {
        Button(onClick = { onStartClick() }) {
            Text(text = "Start")
        }
        KeyInput { tags.add(it) }
        Spacer(modifier = Modifier.width(10.dp))
        Divider(
            thickness = 1.dp, color = Color.Gray,
            modifier = Modifier.width(1.dp).fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(10.dp))
        TagBox(tags) { tags.remove(it) }
    }
}

@Composable
fun KeyInput(onAddClick: (tag: String) -> Unit) {
    Column {
        Text(text = "Add text to filter", color = Color.LightGray, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            var text by remember { mutableStateOf("") }
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.surface,
                        MaterialTheme.shapes.small,
                    )
                    .height(30.dp).width(200.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    if (text.isNotEmpty()) {
                        onAddClick(text)
                        text = ""
                    }
                },
                modifier = Modifier.height(30.dp)
            ) {
                Text(text = "Add")
            }
        }
    }
}