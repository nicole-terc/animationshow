package nstv.animationshow.common.screen.composableApis.children

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions

data class AnimatedItem(
    val key: Int,
    val color: Color,
    val label: String,
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
    val size: DpSize = DpSize(100.dp, 100.dp),
)

fun getDefaultAnimatedItems(count: Int = 5): List<AnimatedItem> =
    (0 until count).map { index -> getDefaultAnimatedItem(index) }

fun getDefaultAnimatedItem(index: Int) =
    AnimatedItem(
        key = index,
        color = TileColor.list[index],
        label = "${enterTransitions.keys.toList()[index]} & ${exitTransitions.keys.toList()[index]}",
        enterTransition = enterTransitions.values.toList()[index],
        exitTransition = exitTransitions.values.toList()[index],
    )
