import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import nstv.animationshow.common.data.SettingsRepository
import nstv.animationshow.common.main.App
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent


fun main() = application {
    val settings = SettingsRepository()
    val size = settings.getWindowSize()
    val position = settings.getWindowPosition().let {
        if (it.x.value < 0 || it.y.value < 0) WindowPosition.PlatformDefault else WindowPosition(it.x, it.y)
    }
    val windowState = rememberWindowState(width = size.width, height = size.height, position = position)

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Animation Show!",
    ) {
        window.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                settings.saveWindowSize(windowState.size)
            }

            override fun componentMoved(e: ComponentEvent?) {
                settings.setWindowPosition(windowState.position.x, windowState.position.y)
            }
        })
        App()
    }
}

