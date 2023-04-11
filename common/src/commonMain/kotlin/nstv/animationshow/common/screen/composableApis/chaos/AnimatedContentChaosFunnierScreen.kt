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
import nstv.animationshow.common.screen.base.LoadingScreen
import nstv.animationshow.common.screen.base.Piece
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Content
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Loading


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentChaosFunnierScreen(
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

