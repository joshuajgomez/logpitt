import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import view.homeScreen

@Composable
@Preview
fun app() {
    MaterialTheme {
        homeScreen()
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "logpitt",
        icon = painterResource("logo_red.ico")
    ) {
        app()
    }
}