import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import nstv.animationshow.common.main.App
import java.awt.Dimension


fun main() = application {
    Window(onCloseRequest = ::exitApplication, state = rememberWindowState(width = 500.dp, height = 800.dp), title = "Animation Show!") {
        App()
    }
}
