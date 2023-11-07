package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.FilterData
import theme.*
import util.getSampleFilters

@Preview
@Composable
fun previewControlBox() {
    controlBox(filterList = getSampleFilters, {}, {}, {}, {})
}

@Composable
fun controlBox(
    filterList: List<FilterData>,
    onStartClick: (isStart: Boolean) -> Unit,
    onRefreshClick: () -> Unit,
    onFilterAdded: (text: String) -> Unit,
    onFilterRemoved: (filter: FilterData) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(color = Gray60)
            .padding(all = 10.dp),
    ) {

        ButtonBox(onStartClick, onRefreshClick)

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
    }
}

@Composable
fun ButtonBox(onStartClick: (isStart: Boolean) -> Unit, onRefreshClick: () -> Unit) {
    val isRunning = remember { mutableStateOf(false) }
    Row {
        Icon(
            imageVector = if (isRunning.value) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = "start",
            tint = Color.DarkGray,
            modifier = Modifier
                .background(shape = CircleShape, color = LightGray20)
                .size(30.dp)
                .clickable {
                    if (isRunning.value) {
                        onStartClick(false)
                        isRunning.value = false
                    } else {
                        onStartClick(true)
                        isRunning.value = true
                    }
                }
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
    }
}

@Composable
fun keyInput(onAddClick: (tag: String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(10.dp))
        var text by remember { mutableStateOf("") }
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .background(
                    MaterialTheme.colors.surface,
                    MaterialTheme.shapes.small,
                )
                .height(30.dp).width(200.dp),
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
        Spacer(modifier = Modifier.width(10.dp))
    }
}