package nstv.animationshow.common.screen.composableApis.chaos

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import nstv.animationshow.common.design.TileColor


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentChaosLookaheadScreen(
    modifier: Modifier = Modifier,
) {
    var leftSide by remember { mutableStateOf(true) }
    var barsPieState by remember { mutableStateOf<BarsPieState>(getAlignedBarsPie()) }
    var backgroundColor by remember { mutableStateOf(TileColor.BlueSlides.copy(alpha = 0.7f)) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                leftSide = !leftSide
            }
        ) {
            Text(text = "Click to change screen")
        }

        Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
            AnimatedContent(
                targetState = leftSide,
                transitionSpec = {
                    EnterTransition.None with // Enter transition
                            ExitTransition.None // Exit Transition
                },
            ) { state ->

            }
        }
    }
}

