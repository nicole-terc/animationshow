import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import nstv.animationshow.common.main.App
import java.awt.Dimension


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        window.minimumSize = Dimension(300, 800)
        App()
    }
}
