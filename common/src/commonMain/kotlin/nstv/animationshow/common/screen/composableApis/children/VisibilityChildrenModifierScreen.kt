package nstv.animationshow.common.screen.composableApis.children

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VisibilityChildrenAdvancedScreen(
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(true) }
    var showText by remember { mutableStateOf(false) }
    var independentTransitions by remember { mutableStateOf(true) }


    val items = remember {
        (0..4).map { index ->
            AnimatedItem(
                color = TileColor.list[index],
                label = "${enterTransitions.keys.toList()[index]} & ${exitTransitions.keys.toList()[index]}",
                enterTransition = enterTransitions.values.toList()[index],
                exitTransition = exitTransitions.values.toList()[index],
            )

        }
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        CheckBoxLabel(
            modifier = Modifier.fillMaxWidth(),
            text = "Show text",
            checked = showText,
            onCheckedChange = { showText = it }
        )

        CheckBoxLabel(
            modifier = Modifier.fillMaxWidth(),
            text = "Independent transitions",
            checked = independentTransitions,
            onCheckedChange = { independentTransitions = it }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isVisible = !isVisible
            }
        ) {
            Text(text = "Click to toggle visibility")
        }
        AnimatedContent(
            targetState = isVisible,
            transitionSpec = {
                if (independentTransitions) {
                    EnterTransition.None with ExitTransition.None
                } else {
                    slideInHorizontally(spring(dampingRatio = Spring.DampingRatioMediumBouncy)) with fadeOut()
                }
            }
        ) { state ->
            if (state) {
                Column(modifier = Modifier.fillMaxSize()) {
                    items.forEach { item ->
                        ColorScreen(
                            color = item.color,
                            modifier = Modifier.fillMaxWidth().weight(1f).animateEnterExit(
                                enter = if (independentTransitions) item.enterTransition else EnterTransition.None,
                                exit = if (independentTransitions) item.exitTransition else ExitTransition.None,
                                label = item.label
                            )
                        ) {
                            if (showText) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    text = item.label,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            } else {
                Column(Modifier.fillMaxSize()) { }
            }
        }
    }
}
