package nstv.animationshow.common.screen.composableApis.chaos

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.screen.base.LoadingScreen
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Content
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Loading

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentChaosFunScreen(
    modifier: Modifier = Modifier,
) {
    var uiState by remember { mutableStateOf<ChaosUiState>(Loading) }
    var alternateStates by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(TileColor.BlueSlides.copy(alpha = 0.7f)) }
    var withBottomBar by remember { mutableStateOf(true) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {

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
                    is Content -> if (alternateStates) Loading else getRandomContent(withBottomBar = withBottomBar)
                }
                withBottomBar = !withBottomBar
            }
        ) {
            Text(text = "Click to change screen")
        }
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
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
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(vertical = Grid.Two),
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(Grid.Half),
                                    modifier = Modifier.fillMaxWidth(0.8f)
                                ) {
                                    state.barsPieState.bars.forEach { bar ->
                                        Bar(
                                            bar = bar,
                                            modifier = Modifier
                                                .animateEnterExit(
                                                    enter = expandHorizontally(animationSpec = tween(delayMillis = bar.id * 100)) { 0 },
                                                    exit = shrinkHorizontally { 0 }
                                                )
                                        )
                                    }
                                }

                                Pie(
                                    pieces = state.barsPieState.pie,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .animateEnterExit(
                                            enter = scaleIn(tween(delayMillis = 600)) + fadeIn(tween(delayMillis = 600)),
                                            exit = scaleOut() + fadeOut(),
                                        )
                                )
                            }
                            state.bottomBar?.let { bottomBar ->
                                Text(
                                    text = bottomBar.label,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                        .animateEnterExit(
                                            enter = slideInVertically(animationSpec = tween(delayMillis = 800) ) { it },
                                            exit = slideOutVertically { it }
                                        )
                                        .background(color = bottomBar.color)
                                        .padding(Grid.Half)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

