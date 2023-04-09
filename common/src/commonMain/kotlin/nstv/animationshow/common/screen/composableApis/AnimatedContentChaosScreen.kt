package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import kotlin.random.Random
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.screen.base.LoadingScreen
import nstv.animationshow.common.screen.base.Piece
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions
import nstv.animationshow.common.screen.base.getRandomPieces
import nstv.animationshow.common.screen.base.piePieces
import nstv.animationshow.common.screen.composableApis.ChaosUiState.Content
import nstv.animationshow.common.screen.composableApis.ChaosUiState.Loading

sealed interface ChaosUiState {
    object Loading : ChaosUiState
    data class Content(
        val bars: List<Piece> = emptyList(),
        val pie: List<Piece> = emptyList(),
    ) : ChaosUiState
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentChaosScreen(
    modifier: Modifier = Modifier,
) {
    var uiState by remember { mutableStateOf<ChaosUiState>(Loading) }
    var alternateStates by remember { mutableStateOf(true) }
    var enterTransitionIndex by remember { mutableStateOf(0) }
    var exitTransitionIndex by remember { mutableStateOf(0) }
    var backgroundColor by remember { mutableStateOf(TileColor.list.first()) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        DropDownWithArrows(
            modifier = modifier.fillMaxWidth(),
            options = enterTransitions.keys.toList(),
            onSelectionChanged = { enterTransitionIndex = it },
            label = "Enter:"
        )

        DropDownWithArrows(
            modifier = modifier.fillMaxWidth(),
            options = exitTransitions.keys.toList(),
            onSelectionChanged = { exitTransitionIndex = it },
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
                    is Loading -> Content(
                        bars = getRandomPieces(),
                        pie = piePieces()
                    )

                    is Content -> if (alternateStates) Loading else Content(
                        bars = getRandomPieces(),
                        pie = piePieces()
                    )
                }
            }
        ) {
            Text(text = "Click to change screen")
        }
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor.copy(alpha = 0.8f))) {
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
                            it.bars.forEach { bar ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(bar.percentage * 0.8f)
                                        .height(Grid.Four)
                                        .background(bar.color),
                                ) {
                                    Text(
                                        modifier = Modifier.align(alignment = Alignment.CenterEnd).padding(Grid.Half),
                                        text = "#${bar.id} Bar",
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }
                            }
                            Box(modifier = Modifier
                                .padding(top = Grid.Four)
                                .fillMaxWidth(0.8f)
                                .aspectRatio(1f)
                                .align(Alignment.CenterHorizontally)
                                .drawBehind {
                                    var currentAngle = 0f
                                    it.pie.forEach {
                                        val sweepAngle = 360f * it.percentage
                                        drawArc(
                                            color = it.color,
                                            startAngle = currentAngle,
                                            sweepAngle = sweepAngle,
                                            useCenter = true,
                                            style = Fill
                                        )
                                        currentAngle += sweepAngle
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
