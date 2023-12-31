package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.FilterData
import theme.*

@Preview
@Composable
fun previewControlBox() {
    ButtonBox()
}

@Composable
fun controlBox(
    isPaused: Boolean = false,
    filterList: List<FilterData> = listOf(),
    onStartClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onFilterAdded: (text: String) -> Unit = {},
    onFilterRemoved: (filter: FilterData) -> Unit = {},
    onFileUploadClick: () -> Unit = {},
    onGoDownClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(color = Gray60)
            .padding(all = 10.dp),
    ) {

        ButtonBox(isPaused, onStartClick, onRefreshClick, onFileUploadClick)

        Divider(
            thickness = 1.dp, color = Color.Gray,
            modifier = Modifier.width(1.dp).fillMaxHeight()
        )

        keyInput { onFilterAdded(it) }

        Divider(
            thickness = 1.dp, color = Color.Gray,
            modifier = Modifier.width(1.dp).fillMaxHeight()
        )

        filterBox(filterList) { onFilterRemoved(it) }

        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = "Jump to now",
            tint = Color.DarkGray,
            modifier = Modifier
                .background(shape = CircleShape, color = LightGray20)
                .size(30.dp)
                .clickable { onGoDownClick() }
                .padding(all = 5.dp)
        )
    }
}

@Composable
fun ButtonBox(
    isPaused: Boolean = false,
    onStartClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onFileUploadClick: () -> Unit = {},
) {
    Row {
        Icon(
            imageVector = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
            contentDescription = "start",
            tint = Color.DarkGray,
            modifier = Modifier
                .background(shape = CircleShape, color = LightGray20)
                .size(30.dp)
                .clickable { onStartClick() }
                .padding(all = 5.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Refresh",
            tint = Color.DarkGray,
            modifier = Modifier
                .background(shape = CircleShape, color = LightGray20)
                .size(30.dp)
                .clickable { onRefreshClick() }
                .padding(all = 5.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector = Icons.Default.UploadFile,
            contentDescription = "upload",
            tint = Color.DarkGray,
            modifier = Modifier
                .background(shape = CircleShape, color = LightGray20)
                .size(30.dp)
                .clickable { onFileUploadClick() }
                .padding(all = 5.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun keyInput(onAddClick: (tag: String) -> Unit = {}) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(10.dp))
        var text by remember { mutableStateOf("") }

        FilterTextField(text, onValueChange = { text = it })

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
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun FilterTextField(text: String, onValueChange: (text: String) -> Unit) {
    BasicTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .background(
                color = LightGray20,
                shape = RoundedCornerShape(5.dp),
            )
            .wrapContentHeight()
            .width(200.dp)
            .padding(all = 5.dp),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                filterIcon()
                Box(Modifier.weight(1f).padding(start = 5.dp)) {
                    if (text.isEmpty()) Text(
                        "Add text to filter",
                        style = LocalTextStyle.current.copy(
                            color = LightGray10,
                            fontSize = 15.sp
                        )
                    )
                    innerTextField()
                }
            }
        }

    )
}

@Composable
fun filterIcon() {
    Icon(
        imageVector = Icons.Default.FilterList,
        contentDescription = "filter",
        modifier = Modifier.size(20.dp)
    )
}
