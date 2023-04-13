package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.nextIndexLoop
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VisibilityScreen(
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(true) }
    var enterTransitionIndex by remember { mutableStateOf(3) }
    var exitTransitionIndex by remember { mutableStateOf(3) }
    var colorIndex by remember { mutableStateOf(1) }

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
                selectedIndex = enterTransitionIndex,
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
                selectedIndex = exitTransitionIndex,
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isVisible = !isVisible
            }
        ) {
            Text(text = "Click to toggle visibility")
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = enterTransitions.values.toList()[enterTransitionIndex],
            exit = exitTransitions.values.toList()[exitTransitionIndex],
        ) {
            val color by transition.animateColor(
                transitionSpec = { spring(stiffness = Spring.StiffnessLow) }
            ) { state ->
                if (state == EnterExitState.Visible) {
                    TileColor.map.values.toList()[colorIndex]
                } else {
                    TileColor.Red
                }
            }

            val progress by transition.animateFloat(
                transitionSpec = { spring(stiffness = Spring.StiffnessLow) }
            ) { state ->
                if (state == EnterExitState.Visible) 1f else 0f
            }

            val roundCorners by transition.animateDp { state ->
                if (state == EnterExitState.Visible) 0.dp else 500.dp
            }

            ColorScreen(
                color = color,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(roundCorners))
                    .clickable { colorIndex = TileColor.list.nextIndexLoop(colorIndex) }) {
                Text(
                    text = if (progress == 1f) "I'm visible! :D" else "D:",
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}
