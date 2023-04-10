@file:OptIn(ExperimentalTextApi::class)

package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import kotlinx.coroutines.delay
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.nextItemLoop
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions
import nstv.animationshow.common.screen.composableApis.SubContent.*


private enum class SubContent {
    FullScreen,
    Quarter,
    HalfVertical,
    HalfHorizontal,
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSizeScreen(
    modifier: Modifier = Modifier,
) {
    var currentSubScreen by remember { mutableStateOf(FullScreen) }
    var enterTransitionIndex by remember { mutableStateOf(enterTransitions.keys.indexOf("fadeIn delay")) }
    var exitTransitionIndex by remember { mutableStateOf(exitTransitions.keys.indexOf("fadeOut slow")) }
    var useHalfSizes by remember { mutableStateOf(false) }

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

        CheckBoxLabel(
            modifier = Modifier.fillMaxWidth(),
            text = "Use half sizes",
            checked = useHalfSizes,
            onCheckedChange = {
                useHalfSizes = it
                currentSubScreen = Quarter
            }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                currentSubScreen =
                    if (!useHalfSizes && currentSubScreen == Quarter) {
                        FullScreen
                    } else {
                        values().nextItemLoop(currentSubScreen)
                    }
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
                            if (targetSize.height > initialSize.height) {
                                keyframes {
                                    IntSize(targetSize.width, initialSize.height) at 300
                                    durationMillis = 600
                                }
                            } else {
                                keyframes {
                                    IntSize(initialSize.width, targetSize.height) at 100
                                    durationMillis = 600
                                }
                            }
                        }
            },
        ) { subContent ->
//            val contentModifier =
            when (subContent) {
                FullScreen -> ColorScreen(Modifier.fillMaxSize(), color = TileColor.Purple)
                Quarter -> ColorScreen(Modifier.fillMaxSize(0.5f), color = TileColor.Red)
                HalfVertical -> ColorScreen(Modifier.fillMaxHeight().fillMaxWidth(0.5f), color = TileColor.Green)
                HalfHorizontal -> ColorScreen(Modifier.fillMaxHeight(0.5f).fillMaxWidth(), color = TileColor.Blue)
            }

//            ColorScreen(modifier = contentModifier)

        }
    }
}
