package nstv.animationshow.common.screen.composableApis.chaos

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Fill
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.screen.base.LoadingScreen
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Content
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Loading


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentChaosScreen(
    modifier: Modifier = Modifier,
) {
    var uiState by remember { mutableStateOf<ChaosUiState>(Loading) }
    var alternateStates by remember { mutableStateOf(true) }
    var enterTransitionIndex by remember { mutableStateOf(enterTransitions.keys.indexOf("fadeIn + expandH")) }
    var exitTransitionIndex by remember { mutableStateOf(exitTransitions.keys.indexOf("fadeOut + shrinkH")) }
    var backgroundColor by remember { mutableStateOf(TileColor.BlueSlides) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        DropDownWithArrows(
            modifier = modifier.fillMaxWidth(),
            options = enterTransitions.keys.toList(),
            onSelectionChanged = { enterTransitionIndex = it },
            selectedIndex = enterTransitionIndex,
            label = "Enter:"
        )

        DropDownWithArrows(
            modifier = modifier.fillMaxWidth(),
            options = exitTransitions.keys.toList(),
            onSelectionChanged = { exitTransitionIndex = it },
            selectedIndex = exitTransitionIndex,
            label = "Exit:"
        )

        CheckBoxLabel(
            text = "Alternate states",
            checked = alternateStates,
            onCheckedChange = { alternateStates = it }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                uiState = when (uiState) {
                    is Loading -> getRandomContent()
                    is Content -> if (alternateStates) Loading else getRandomContent()
                }
            }
        ) {
            Text(text = "Click to change screen")
        }
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor.copy(alpha = 0.7f))) {
            AnimatedContent(
                targetState = uiState,
                transitionSpec = {
                    enterTransitions.values.toList()[enterTransitionIndex] with // Enter transition
                            exitTransitions.values.toList()[exitTransitionIndex] // Exit Transition
                },
            ) {
                when (it) {
                    is Loading -> {
                        LoadingScreen(modifier = Modifier.fillMaxSize())
                    }

                    is Content -> {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = Grid.Two),
                            verticalArrangement = Arrangement.spacedBy(Grid.Half),
                        ) {
                            it.barsPieState.bars.forEach { bar ->
                                Bar(bar)
                            }
                            Pie(
                                pieces = it.barsPieState.pie,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}
