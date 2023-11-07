package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import util.getSampleTags

@Composable
fun tagBox(tagList: List<String>, onRemoveClick: (text: String) -> Unit) {
    LazyHorizontalGrid(
        rows = GridCells.Adaptive(20.dp),
        modifier = Modifier.padding(start = 10.dp)
    ) {
        items(items = tagList) {
            TagItem(it) { onRemoveClick(it) }
        }
    }
}

@Composable
fun TagItem(text: String = "", onRemoveClick: () -> Unit = {}) {
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = Modifier
            .padding(end = 5.dp, bottom = 5.dp)
            .background(
                shape = RoundedCornerShape(10.dp),
                color = Color.LightGray
            )
            .height(25.dp)
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Text(text = text, fontSize = 15.sp)
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            modifier = Modifier
                .size(15.dp)
                .clickable { onRemoveClick() }
        )
    }
}

@Preview
@Composable
fun PreviewTagBox() {
    tagBox(getSampleTags) {}
}