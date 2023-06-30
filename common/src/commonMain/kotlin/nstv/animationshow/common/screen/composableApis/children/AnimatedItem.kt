package nstv.animationshow.common.screen.composableApis.children

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.graphics.Color
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions

data class AnimatedItem(
    val key: Int,
    val color: Color,
    val label: String,
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
    val expanded: Boolean = false,
    val showChildren: Boolean = false,
)

fun getDefaultAnimatedItems(count: Int = 5): List<AnimatedItem> =
    (0 until count).map { index -> getDefaultAnimatedItem(index) }

fun getDefaultAnimatedItem(key: Int): AnimatedItem {
    val index = key % TileColor.list.size
    return AnimatedItem(
        key = key,
        color = TileColor.list[index],
        label = "${enterTransitions.keys.toList()[index]} & ${exitTransitions.keys.toList()[index]}",
        enterTransition = enterTransitions.values.toList()[index],
        exitTransition = exitTransitions.values.toList()[index],
    )
}
