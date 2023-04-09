package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VisibilityChildrenScreen(
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(true) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
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
            enter = EnterTransition.None,
            exit = ExitTransition.None,
        ) {
            Column {
                for (transitionIndex in listOf(2, 3, 7, 9)) { //2..9 step 2) {
                    val label =
                        "${enterTransitions.keys.toList()[transitionIndex]} + ${exitTransitions.keys.toList()[transitionIndex]}"
                    ColorScreen(
                        color = TileColor.list[transitionIndex],
                        modifier = Modifier.fillMaxWidth().weight(1f).animateEnterExit(
                            enter = enterTransitions.values.toList()[transitionIndex],
                            exit = exitTransitions.values.toList()[transitionIndex],
                            label = label
                        )
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge,
                            text = label,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
