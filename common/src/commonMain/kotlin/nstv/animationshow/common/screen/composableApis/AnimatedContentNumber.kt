package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.screen.base.Tile
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.enterTransitionsOpposite
import nstv.animationshow.common.screen.base.exitTransitions
import nstv.animationshow.common.screen.base.exitTransitionsOpposite


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentNumberScreen(
    modifier: Modifier = Modifier,
) {
    var enterTransitionIndex by remember { mutableStateOf(4) }
    var exitTransitionIndex by remember { mutableStateOf(4) }
    var oppositeDirections by remember { mutableStateOf(true) }
    var backgroundColor by remember { mutableStateOf(TileColor.list[3]) }
    var showBorder by remember { mutableStateOf(true) }
    var tapCounter by remember { mutableStateOf(0) }

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
            modifier = modifier.fillMaxWidth(),
            checked = oppositeDirections,
            onCheckedChange = { oppositeDirections = it },
            text = "Opposite Directions"
        )

        CheckBoxLabel(
            modifier = modifier.fillMaxWidth(),
            checked = showBorder,
            onCheckedChange = { showBorder = it },
            text = "Show Border"
        )

        Box(modifier = Modifier.fillMaxSize().background(backgroundColor.copy(alpha = 0.7f))) {

            Row(
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                Button(
                    modifier = Modifier.padding(Grid.Two),
                    onClick = { tapCounter = (tapCounter - 1).coerceAtLeast(0) },
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "minus")
                }
                Button(
                    modifier = Modifier.padding(Grid.Two),
                    onClick = { tapCounter++ },
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "plus")
                }

            }
            AnimatedContent(
                modifier = Modifier.align(Alignment.Center),
                targetState = tapCounter,
                contentAlignment = Alignment.Center,
                transitionSpec = {
                    if (!oppositeDirections) {
                        enterTransitions.values.toList()[enterTransitionIndex] with // Enter transition
                                exitTransitions.values.toList()[exitTransitionIndex] using // Exit Transition
                                SizeTransform(clip = false)
                    } else {
                        if (targetState > initialState) {
                            enterTransitions.values.toList()[enterTransitionIndex] with // Enter transition
                                    exitTransitionsOpposite.values.toList()[exitTransitionIndex] using // Exit Transition
                                    SizeTransform(clip = false)
                        } else {
                            enterTransitionsOpposite.values.toList()[enterTransitionIndex] with // Enter transition
                                    exitTransitions.values.toList()[exitTransitionIndex] using // Exit Transition
                                    SizeTransform(clip = false)
                        }
                    }
                },
            ) { state ->
                Text(
                    modifier = Modifier.padding(Grid.Two).align(Alignment.Center).defaultMinSize(50.dp)
                        .drawBehind {
                            if (showBorder) {
                                drawCircle(
                                    color = TileColor.Blue.copy(alpha = 0.9f),
                                    radius = size.maxDimension * 0.75f,
                                    style = Fill
                                )
                            }
                        },
                    text = "$state",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}
