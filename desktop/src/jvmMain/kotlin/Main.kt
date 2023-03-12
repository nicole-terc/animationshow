import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import nstv.animationshow.common.main.App


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
