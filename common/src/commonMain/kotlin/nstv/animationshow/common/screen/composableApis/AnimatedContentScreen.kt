package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.nextItemLoop
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.LoadingScreen
import nstv.animationshow.common.screen.composableApis.SubScreen.*
import nstv.animationshow.common.screen.enterTransitions
import nstv.animationshow.common.screen.exitTransitions
import nstv.animationshow.common.screen.squareGrid.SquareGrid


private enum class SubScreen {
    LoadingSubScreen,
    ColorSubScreen,
    GridSubScreen,
    None,
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentScreen(
    modifier: Modifier = Modifier,
) {
    var currentSubScreen by remember { mutableStateOf(LoadingSubScreen) }
    var enterTransitionIndex by remember { mutableStateOf(0) }
    var exitTransitionIndex by remember { mutableStateOf(0) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                text = "Enter Transition: "
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
                text = "Exit Transition: "
            )
            DropDownWithArrows(
                modifier = modifier.fillMaxWidth().weight(3f),
                options = exitTransitions.keys.toList(),
                onSelectionChanged = { exitTransitionIndex = it },
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                currentSubScreen = SubScreen.values().nextItemLoop(currentSubScreen)
            }
        ) {
            Text(text = "Click to change screen")
        }
        AnimatedContent(
            targetState = currentSubScreen,
            transitionSpec = {
                enterTransitions.values.toList()[enterTransitionIndex] with // Enter transition
                        exitTransitions.values.toList()[exitTransitionIndex] // Exit Transition
            },
        ) {
            when (it) {
                LoadingSubScreen -> LoadingScreen()
                ColorSubScreen -> ColorScreen()
                GridSubScreen -> SquareGrid()
                None -> {}
            }
        }
    }
}
