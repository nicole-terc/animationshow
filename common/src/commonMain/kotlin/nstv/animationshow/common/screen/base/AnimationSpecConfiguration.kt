package nstv.animationshow.common.screen.base

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.RepeatMode.Restart
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessHigh
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.design.components.SliderLabelValue
import nstv.animationshow.common.screen.base.AnimationSpecType.Keyframes
import nstv.animationshow.common.screen.base.AnimationSpecType.Repeatable
import nstv.animationshow.common.screen.base.AnimationSpecType.Snap
import nstv.animationshow.common.screen.base.AnimationSpecType.Tween


const val MaxDurationInMillis = 3000f

data class AnimationSpecValues(
    // Tween
    val durationMillis: Int = AnimationConstants.DefaultDurationMillis,
    val delayMillis: Int = 0,
    val easing: Easing = FastOutSlowInEasing,

    //Spring
    val dampingRatio: Float = Spring.DampingRatioMediumBouncy,
    val stiffness: Float = Spring.StiffnessMediumLow,
    val visibilityThreshold: Float? = null,

    //Keyframes
    // durationMillis
    val keyFrames: List<Triple<Float, Int, Easing>> = listOf(
        Triple(0f, 0, LinearOutSlowInEasing),
        Triple(0.2f, 15, FastOutLinearInEasing),
        Triple(0.4f, 75, LinearEasing),
        Triple(0.4f, 225, LinearEasing),
        Triple(1f, durationMillis, LinearEasing),
    ),
    val keyFramesIntOffset: List<Triple<IntOffset, Int, Easing>> = listOf(
        Triple(IntOffset(0, -500), 150, FastOutLinearInEasing),
    ),

    //Repeatable
    val iterations: Int = 3,
    val repeatMode: RepeatMode = Restart,
    val repeatSubAnimationSpecIndex: Int = 0,
    val initialStartOffset: StartOffset = StartOffset(0),

    //snap
    // delayMillis
) {
    fun getAnimationSpec(animationSpecType: AnimationSpecType): FiniteAnimationSpec<Float> {
        return when (animationSpecType) {
            Tween -> tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing,
            )

            AnimationSpecType.Spring ->
                spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness,
                    visibilityThreshold = visibilityThreshold,
                )

            Keyframes -> keyframes {
                durationMillis
                keyFrames.forEach { (fraction, duration, easing) ->
                    fraction at duration with easing
                }
            }

            Repeatable -> repeatable(
                iterations = iterations,
                animation = defaultDurationBasedAnimationSpecs.values.toList()[repeatSubAnimationSpecIndex],
                repeatMode = repeatMode,
                initialStartOffset = initialStartOffset,
            )

            Snap -> snap(
                delayMillis = delayMillis,
            )
        }
    }

    fun getAnimationSpecIntOffset(animationSpecType: AnimationSpecType): FiniteAnimationSpec<IntOffset> {
        return when (animationSpecType) {
            Tween -> tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing,
            )

            AnimationSpecType.Spring ->
                spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness,
                )

            Keyframes -> keyframes {
                durationMillis
                keyFramesIntOffset.forEach { (fraction, duration, easing) ->
                    fraction at duration with easing
                }
            }

            Repeatable -> repeatable(
                iterations = iterations,
                animation = defaultDurationBasedAnimationSpecsIntOffset.values.toList()[repeatSubAnimationSpecIndex],
                repeatMode = repeatMode,
                initialStartOffset = initialStartOffset,
            )

            Snap

            -> snap(
                delayMillis = delayMillis,
            )
        }
    }
}

@Composable
fun AnimationSpecConfiguration(
    animationSpecType: AnimationSpecType,
    animationSpecValues: AnimationSpecValues,
    onValuesChange: (AnimationSpecValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (animationSpecType) {
        Tween -> TweenConfiguration(
            animationSpecValues = animationSpecValues,
            onValuesChange = onValuesChange,
            modifier = modifier,
        )

        AnimationSpecType.Spring -> SpringConfiguration(
            animationSpecValues = animationSpecValues,
            onValuesChange = onValuesChange,
            modifier = modifier,
        )

        Keyframes -> KeyframesConfiguration(
            animationSpecValues = animationSpecValues,
            onValuesChange = onValuesChange,
            modifier = modifier,
        )

        Repeatable -> RepeatableConfiguration(
            animationSpecValues = animationSpecValues,
            onValuesChange = onValuesChange,
            modifier = modifier,
        )

        Snap -> SnapConfiguration(
            animationSpecValues = animationSpecValues,
            onValuesChange = onValuesChange,
            modifier = modifier,
        )
    }
}

@Composable
fun TweenConfiguration(
    animationSpecValues: AnimationSpecValues,
    onValuesChange: (AnimationSpecValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        DropDownWithArrows(
            options = easings.keys.toList(),
            selectedIndex = easings.values.toList().indexOf(animationSpecValues.easing),
            onSelectionChanged = {
                onValuesChange(animationSpecValues.copy(easing = easings.values.toList()[it]))
            },
            label = "Easing:",
            modifier = Modifier.padding(bottom = Grid.Half),
        )
        SliderLabelValue(
            text = "Duration:",
            value = animationSpecValues.durationMillis.toFloat(),
            valueRange = 0f..MaxDurationInMillis,
            onValueChange = {
                onValuesChange(animationSpecValues.copy(durationMillis = it.toInt()))
            }
        )
        SliderLabelValue(
            text = "Delay:",
            value = animationSpecValues.delayMillis.toFloat(),
            valueRange = 0f..MaxDurationInMillis,
            onValueChange = {
                onValuesChange(animationSpecValues.copy(delayMillis = it.toInt()))
            }
        )
    }
}

@Composable
fun SpringConfiguration(
    animationSpecValues: AnimationSpecValues,
    onValuesChange: (AnimationSpecValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SliderLabelValue(
            text = "DampingRatio:",
            value = animationSpecValues.dampingRatio * 100f,
            valueRange = 0f..100f,
            onValueChange = {
                onValuesChange(animationSpecValues.copy(dampingRatio = it / 100f))
            }
        )
        SliderLabelValue(
            text = "Stiffness:",
            value = animationSpecValues.stiffness,
            valueRange = 0f..StiffnessHigh,
            onValueChange = {
                onValuesChange(animationSpecValues.copy(stiffness = it))
            }
        )
    }
}

@Composable
fun KeyframesConfiguration(
    animationSpecValues: AnimationSpecValues,
    onValuesChange: (AnimationSpecValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SliderLabelValue(
            text = "Duration:",
            value = animationSpecValues.durationMillis.toFloat(),
            valueRange = 0f..MaxDurationInMillis,
            onValueChange = {
                onValuesChange(animationSpecValues.copy(durationMillis = it.toInt()))
            }
        )
    }
}

@Composable
fun RepeatableConfiguration(
    animationSpecValues: AnimationSpecValues,
    onValuesChange: (AnimationSpecValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        DropDownWithArrows(
            options = defaultDurationBasedAnimationSpecs.keys.toList(),
            selectedIndex = animationSpecValues.repeatSubAnimationSpecIndex,
            onSelectionChanged = {
                onValuesChange(
                    animationSpecValues.copy(
                        repeatSubAnimationSpecIndex = it,
                    )
                )
            },
            label = "AnimationSpec:",
            modifier = Modifier.padding(bottom = Grid.Half),
        )
        DropDownWithArrows(
            options = repeatableModes.keys.toList(),
            selectedIndex = repeatableModes.values.toList().indexOf(animationSpecValues.repeatMode),
            onSelectionChanged = {
                onValuesChange(animationSpecValues.copy(repeatMode = repeatableModes.values.toList()[it]))
            },
            label = "RepeatMode:",
            modifier = Modifier.padding(bottom = Grid.Half),
        )
        SliderLabelValue(
            text = "iterations:",
            value = animationSpecValues.iterations.toFloat(),
            valueRange = 0f..10f,
            onValueChange = {
                onValuesChange(animationSpecValues.copy(iterations = it.toInt()))
            }
        )
        SliderLabelValue(
            text = "Delay:",
            value = animationSpecValues.initialStartOffset.offsetMillis.toFloat(),
            valueRange = 0f..MaxDurationInMillis,
            onValueChange = {
                onValuesChange(
                    animationSpecValues.copy(
                        initialStartOffset = StartOffset(it.toInt())
                    )
                )
            }
        )
    }
}

@Composable
fun SnapConfiguration(
    animationSpecValues: AnimationSpecValues,
    onValuesChange: (AnimationSpecValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SliderLabelValue(
            text = "Delay:",
            value = animationSpecValues.delayMillis.toFloat(),
            valueRange = 0f..MaxDurationInMillis,
            onValueChange = {
                onValuesChange(animationSpecValues.copy(delayMillis = it.toInt()))
            }
        )
    }
}
