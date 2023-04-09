package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExitTransition.Companion
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
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
import androidx.compose.material.CircularProgressIndicator
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
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.screen.base.LoadingScreen
import nstv.animationshow.common.screen.base.Piece
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions
import nstv.animationshow.common.screen.base.piePieces
import nstv.animationshow.common.screen.composableApis.ChaosFunUiState.Content
import nstv.animationshow.common.screen.composableApis.ChaosFunUiState.Loading

sealed interface ChaosFunUiState {
    object Loading : ChaosFunUiState
    data class Content(
        val bars: List<Piece> = emptyList(),
        val pie: List<Piece> = emptyList(),
    ) : ChaosFunUiState
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentChaosFunScreen(
    modifier: Modifier = Modifier,
) {
    var uiState by remember { mutableStateOf<ChaosFunUiState>(Loading) }
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

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                uiState = when (uiState) {
                    is Loading -> Content(
                        bars = List(5) {
                            Piece(
                                it + 1,
                                Random.nextFloat(),
                                color = TileColor.list[it + 1]
                            )
                        },
                        pie = piePieces()
                    )

                    is Content -> Loading
                }
            }
        ) {
            Text(text = "Click to change screen")
        }
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor.copy(0.8f))) {
            AnimatedContent(
                targetState = uiState,
                transitionSpec = {
                    EnterTransition.None with // Enter transition
                            ExitTransition.None // Exit Transition
                },
            ) { state ->
                when (state) {
                    is Loading -> {
                        LoadingScreen(
                            modifier = Modifier
                                .animateEnterExit(
                                    enter = fadeIn(
                                        tween(delayMillis = 200, durationMillis = 500)
                                    ),
                                    exit = fadeOut()
                                )
                        )
                    }

                    is Content -> {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = Grid.Two),
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(Grid.Half),
                                modifier = Modifier.fillMaxWidth(0.8f)
                            ) {
                                state.bars.forEach { bar ->
                                    Box(
                                        modifier = Modifier
                                            .animateEnterExit(
                                                enter = expandHorizontally() { -it },
                                                exit = shrinkHorizontally { -it }
                                            )
                                            .fillMaxWidth(bar.percentage)
                                            .height(Grid.Four)
                                            .background(bar.color)

                                    ) {
                                        Text(
                                            modifier = Modifier.align(alignment = Alignment.CenterEnd)
                                                .padding(Grid.Half),
                                            text = "#${bar.id} Bar",
                                            style = MaterialTheme.typography.bodyLarge,
                                        )
                                    }
                                }
                            }
                            Box(modifier = Modifier
                                .padding(top = Grid.Four)
                                .fillMaxWidth(0.8f)
                                .aspectRatio(1f)
                                .align(Alignment.CenterHorizontally)
                                .animateEnterExit(
                                    enter = scaleIn(),
                                    exit = scaleOut(),
                                )
                                .drawBehind {
                                    var currentAngle = 0f
                                    state.pie.forEach {
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

