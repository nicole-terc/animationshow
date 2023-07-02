package nstv.animationshow.common.screen.base

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.graphics.Color
import nstv.animationshow.common.design.TileColor

data class Thumbler(
    val id: Int,
    val color: Color,
    val label: String,
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
    val expanded: Boolean = false,
    val showChildren: Boolean = false,
)

fun getThumblerList(count: Int = 5): List<Thumbler> =
    (0 until count).map { index -> getThumbler(index) }

fun getThumbler(key: Int): Thumbler {
    val index = key % TileColor.list.size
    return Thumbler(
        id = key,
        color = TileColor.list[index],
        label = "${enterTransitions.keys.toList()[index]} & ${exitTransitions.keys.toList()[index]}",
        enterTransition = enterTransitions.values.toList()[index],
        exitTransition = exitTransitions.values.toList()[index],
    )
}
