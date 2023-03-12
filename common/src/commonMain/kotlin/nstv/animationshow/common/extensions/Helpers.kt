package nstv.animationshow.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import kotlin.math.min

@Composable
fun IntSize.toDpSizeSquared(): DpSize {
    val size = min(this.width, this.height)
    with(LocalDensity.current) {
        return DpSize(
            width = size.toDp(),
            height = size.toDp()
        )
    }
}
