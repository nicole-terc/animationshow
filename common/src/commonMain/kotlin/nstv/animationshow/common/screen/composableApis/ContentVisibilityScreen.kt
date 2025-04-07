package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.nextIndexLoop
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContentVisibilityScreen(
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(true) }
    var enterTransitionIndex by remember { mutableStateOf(0) }
    var exitTransitionIndex by remember { mutableStateOf(0) }
    var colorIndex by remember { mutableStateOf(1) }
    var useDefaultAnimation by remember { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                text = "Enter: "
            )
            DropDownWithArrows(
                modifier = modifier.fillMaxWidth().weight(3f),
                options = enterTransitions.keys.toList(),
                onSelectionChanged = { enterTransitionIndex = it },
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                text = "Exit: "
            )
            DropDownWithArrows(
                modifier = modifier.fillMaxWidth().weight(3f),
                options = exitTransitions.keys.toList(),
                onSelectionChanged = { exitTransitionIndex = it },
            )
        }
        CheckBoxLabel(
            modifier = Modifier.fillMaxWidth(),
            text = "Use default animation",
            checked = useDefaultAnimation,
            onCheckedChange = { useDefaultAnimation = it }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isVisible = !isVisible
            }
        ) {
            Text(text = "Click to toggle visibility")
        }
        AnimatedContent(
            targetState = isVisible,
            transitionSpec = {
                if (useDefaultAnimation) {
                    fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)) with
                            fadeOut(animationSpec = tween(90))
                } else {
                    slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down) togetherWith  // Enter transition
                            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up)// Exit Transition
                }
            },
        ) { state ->
            Column(modifier = Modifier.fillMaxSize()) {
                if (state) {
                    ColorScreen(
                        color = TileColor.map.values.toList()[colorIndex].copy(alpha = 0.8f),
                        modifier = Modifier.fillMaxSize()
                            .clickable { colorIndex = TileColor.list.nextIndexLoop(colorIndex) }) {
                        Text(
                            text = "I'm visible! :D",
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                }
            }
        }
    }
}
