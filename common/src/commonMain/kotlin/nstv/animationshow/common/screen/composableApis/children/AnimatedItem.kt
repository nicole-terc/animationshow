package nstv.animationshow.common.screen.composableApis.children

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.graphics.Color

data class AnimatedItem(
    val color: Color,
    val label: String,
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
)
