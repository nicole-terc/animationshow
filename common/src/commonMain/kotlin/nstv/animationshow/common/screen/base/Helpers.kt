package nstv.animationshow.common.screen.base

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessHigh
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.Spring.StiffnessMedium
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.IntOffset
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.screen.base.AnimationSpecType.*

@OptIn(ExperimentalAnimationApi::class)
val enterTransitions = mapOf(
    "fadeIn + expandV" to fadeIn() + expandVertically(),
    "fadeIn + expandH" to fadeIn() + expandHorizontally { it / 2 },
    "fadeIn" to fadeIn(),
    "scaleIn + slideInH" to scaleIn() + slideInHorizontally { -it / 2 },
    "scaleIn + slideInH I" to scaleIn() + slideInHorizontally { it / 2 },
    "slideIn + fadeIn" to slideIn { IntOffset(it.width / 2, it.height / 2) } + fadeIn(),
    "slideIn" to slideIn { IntOffset(it.width / 2, it.height / 2) },
    "slideInHorizontally" to slideInHorizontally { it / 2 },
    "slideInVertically" to slideInVertically { it / 2 },
    "scaleIn" to scaleIn(),
    "expandIn" to expandIn { it / 2 },
    "expandHorizontally" to expandHorizontally { it / 2 },
    "expandVertically" to expandVertically(),
    "fadeIn delay" to fadeIn(animationSpec = tween(300, 150)),
)

@OptIn(ExperimentalAnimationApi::class)
val enterTransitionsOpposite = mapOf(
    "fadeIn + expandV" to fadeIn() + expandVertically(),
    "fadeIn + expandH" to fadeIn() + expandHorizontally { -it / 2 },
    "fadeIn" to fadeIn(),
    "scaleIn + slideInH" to scaleIn() + slideInHorizontally { it / 2 },
    "scaleIn + slideInH I" to scaleIn() + slideInHorizontally { -it / 2 },
    "slideIn + fadeIn" to slideIn { IntOffset(-it.width / 2, -it.height / 2) } + fadeIn(),
    "slideIn" to slideIn { IntOffset(-it.width / 2, -it.height / 2) },
    "slideInHorizontally" to slideInHorizontally { -it / 2 },
    "slideInVertically" to slideInVertically { -it / 2 },
    "scaleIn" to scaleIn(),
    "expandIn" to expandIn { it / 2 },
    "expandHorizontally" to expandHorizontally { -it / 2 },
    "expandVertically" to expandVertically(),
    "fadeIn delay" to fadeIn(animationSpec = tween(300, 150)),
)

@OptIn(ExperimentalAnimationApi::class)
val exitTransitions = mapOf(
    "fadeOut + shrinkV" to fadeOut() + shrinkVertically { 0 },
    "fadeOut + shrinkH" to fadeOut() + shrinkHorizontally { it / 2 },
    "fadeOut" to fadeOut(),
    "scaleOut + slideOutH" to scaleOut() + slideOutHorizontally { -it / 2 },
    "scaleOut + slideOutH I" to scaleOut() + slideOutHorizontally { it / 2 },
    "slideOut + fadeOut" to slideOut { IntOffset(it.width, it.height) } + fadeOut(),
    "slideOut" to slideOut { IntOffset(it.width, it.height) },
    "slideOutHorizontally" to slideOutHorizontally { it / 2 },
    "slideOutVertically" to slideOutVertically { it / 2 },
    "scaleOut" to scaleOut(),
    "shrinkOut" to shrinkOut { it / 2 },
    "shrinkHorizontally" to shrinkHorizontally { it / 2 },
    "shrinkVertically" to shrinkVertically(),
    "fadeOut slow" to fadeOut(animationSpec = tween(600)),
)

@OptIn(ExperimentalAnimationApi::class)
val exitTransitionsOpposite = mapOf(
    "fadeOut + shrinkV" to fadeOut() + shrinkVertically(),
    "fadeOut + shrinkH" to fadeOut() + shrinkHorizontally { -it / 2 },
    "fadeOut" to fadeOut(),
    "scaleOut + slideOutH" to scaleOut() + slideOutHorizontally { it / 2 },
    "scaleOut + slideOutH I" to scaleOut() + slideOutHorizontally { -it / 2 },
    "slideOut + fadeOut" to slideOut { IntOffset(-it.width, -it.height) } + fadeOut(),
    "slideOut" to slideOut { IntOffset(-it.width, -it.height) },
    "slideOutHorizontally" to slideOutHorizontally { -it / 2 },
    "slideOutVertically" to slideOutVertically { -it / 2 },
    "scaleOut" to scaleOut(),
    "shrinkOut" to shrinkOut { it / 2 },
    "shrinkHorizontally" to shrinkHorizontally { -it / 2 },
    "shrinkVertically" to shrinkVertically(),
    "fadeOut slow" to fadeOut(animationSpec = tween(600)),
)

// ANIMATION SPECS

enum class AnimationSpecType {
    Tween,
    Spring,
    Keyframes,
    Repeatable,
    Snap,
}

val finiteAnimationSpec = mapOf<String, AnimationSpecType>(
    "Tween" to Tween,
    "Spring" to Spring,
    "Keyframes" to Keyframes,
    "Repeatable" to Repeatable,
    "Snap" to Snap,
)

val defaultFiniteAnimationSpec = mapOf<String, FiniteAnimationSpec<Float>>(
    "Tween" to tween(),
    "Spring" to spring(),
    "Keyframes" to keyframes {
        durationMillis = 375
        0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
        0.2f at 15 with FastOutLinearInEasing // for 15-75 ms
        0.4f at 75 // ms
        0.4f at 225 // ms
    },
    "Repeatable" to repeatable(
        iterations = 3,
        animation = tween(durationMillis = 300)
    ),
    "Snap" to snap(),
)

val defaultDurationBasedAnimationSpecs = mapOf<String, DurationBasedAnimationSpec<Float>>(
    "Tween" to tween(),
    "Keyframes" to keyframes {
        durationMillis = 375
        0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
        0.2f at 15 with FastOutLinearInEasing // for 15-75 ms
        0.4f at 75 // ms
        0.4f at 225 // ms
    },
    "Snap" to snap(),
)

val defaultDurationBasedAnimationSpecsIntOffset = mapOf<String, DurationBasedAnimationSpec<IntOffset>>(
    "Tween" to tween(),
    "Keyframes" to keyframes {
        durationMillis = 375
        IntOffset(0, -500) at 150 with FastOutSlowInEasing
    },
    "Snap" to snap(),
)

val easings = mapOf<String, Easing>(
    "FastOutSlowInEasing" to FastOutSlowInEasing,
    "LinearOutSlowInEasing" to LinearOutSlowInEasing,
    "FastOutLinearInEasing" to FastOutLinearInEasing,
    "LinearEasing" to LinearEasing,
)

val repeatableModes = mapOf<String, RepeatMode>(
    "Restart" to RepeatMode.Restart,
    "Reverse" to RepeatMode.Reverse,
)

val dampingRatioOptions = mapOf<String, Float>(
    "No Bouncy" to DampingRatioNoBouncy,
    "Low Bouncy" to DampingRatioLowBouncy,
    "Medium Bouncy" to DampingRatioMediumBouncy,
    "High Bouncy" to DampingRatioHighBouncy,
)

val stiffnessOptions = mapOf<String, Float>(
    "VeryLow" to StiffnessVeryLow,
    "Low" to StiffnessLow,
    "MediumLow" to StiffnessMediumLow,
    "Medium" to StiffnessMedium,
    "High" to StiffnessHigh,
)

val shapes = mapOf<String, Shape>(
    "RoundedCorner" to RoundedCornerShape(Grid.One),
    "Circle" to CircleShape,
)
