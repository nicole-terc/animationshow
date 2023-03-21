package nstv.animationshow.common.screen

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.unit.IntOffset

@OptIn(ExperimentalAnimationApi::class)
val enterTransitions = mapOf(
    "fadeIn" to fadeIn(),
    "slideIn" to slideIn { IntOffset(it.width / 2, it.height / 2) },
    "slideInHorizontally" to slideInHorizontally { it / 2 },
    "slideVertically" to slideInVertically { it / 2 },
    "scaleIn" to scaleIn(),
    "expandIn" to expandIn { it / 2 },
    "expandHorizontally" to expandHorizontally { it / 2 },
    "expandVertically" to expandVertically(),
    "fadeIn + expandV" to fadeIn() + expandVertically(),
    "fadeIn + expandH" to fadeIn() + expandHorizontally { it / 2 },
)

@OptIn(ExperimentalAnimationApi::class)
val exitTransitions = mapOf(
    "fadeOut" to fadeOut(),
    "slideOut" to slideOut { IntOffset(it.width, it.height) },
    "slideOutHorizontally" to slideOutHorizontally { it },
    "slideOutVertically" to slideOutVertically { it },
    "scaleOut" to scaleOut(),
    "shrinkOut" to shrinkOut { it },
    "shrinkHorizontally" to shrinkHorizontally { it },
    "shrinkVertically" to shrinkVertically(),
    "fadeOut + shrinkV" to fadeOut() + shrinkVertically(),
    "fadeOut + shrinkH" to fadeOut() + shrinkHorizontally { it / 2 },
)
