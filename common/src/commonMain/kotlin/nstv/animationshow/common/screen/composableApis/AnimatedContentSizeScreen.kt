@file:OptIn(ExperimentalTextApi::class)

package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.with
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
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.IntSize
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.nextItemLoop
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions
import nstv.animationshow.common.screen.composableApis.SubContent.*


private enum class SubContent {
    FullScreen,
    Quarter,
//    HalfVertical,
//    HalfHorizontal,
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSizeScreen(
    modifier: Modifier = Modifier,
) {
    var currentSubScreen by remember { mutableStateOf(FullScreen) }
    var enterTransitionIndex by remember { mutableStateOf(0) }
    var exitTransitionIndex by remember { mutableStateOf(0) }

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
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                currentSubScreen = values().nextItemLoop(currentSubScreen)
            }
        ) {
            Text(text = "Click to change screen")
        }
        AnimatedContent(
            targetState = currentSubScreen,
            transitionSpec = {
                enterTransitions.values.toList()[enterTransitionIndex] with // Enter transition
                        exitTransitions.values.toList()[exitTransitionIndex] using // Exit transition
                        SizeTransform { initialSize, targetSize ->
                            if (targetState == FullScreen) {
                                keyframes {
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            },
        ) { subContent ->
//            val contentModifier =
            when (subContent) {
                FullScreen -> ColorScreen(Modifier.fillMaxSize())
                Quarter -> ColorScreen(Modifier.fillMaxSize(0.5f))
//                    HalfVertical -> Modifier.fillMaxHeight().fillMaxWidth(0.5f)
//                    HalfHorizontal -> Modifier.fillMaxHeight(0.5f).fillMaxWidth()
            }

//            ColorScreen(modifier = contentModifier)

        }
    }
}
