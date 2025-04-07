package nstv.animationshow.common.data

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.russhwolf.settings.Settings

object SettingsKeys {
    const val windowWidth = "windowWidth"
    const val windowHeight = "windowHeight"
    const val windowX = "windowX"
    const val windowY = "windowY"
}

class SettingsRepository {
    private val settings: Settings by lazy { Settings() }

    fun getWindowSize(): DpSize {
        val width = settings.getInt(SettingsKeys.windowWidth, 500)
        val height = settings.getInt(SettingsKeys.windowHeight, 800)
        return DpSize(width.dp, height.dp)
    }

    fun saveWindowSize(size: DpSize) {
        settings.putInt(SettingsKeys.windowWidth, size.width.value.toInt())
        settings.putInt(SettingsKeys.windowHeight, size.height.value.toInt())
    }

    fun getWindowPosition(): DpOffset {
        val x = settings.getInt(SettingsKeys.windowX, -1)
        val y = settings.getInt(SettingsKeys.windowY, -1)
        return DpOffset(x.dp, y.dp)
    }

    fun setWindowPosition(x: Dp, y: Dp) {
        settings.putInt(SettingsKeys.windowX, x.value.toInt())
        settings.putInt(SettingsKeys.windowY, y.value.toInt())
    }
}
