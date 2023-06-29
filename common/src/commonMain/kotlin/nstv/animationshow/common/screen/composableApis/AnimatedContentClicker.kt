package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.nextItemLoop
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions
import nstv.animationshow.common.screen.base.exitTransitionsOpposite


private data class ClickerState(
    val color: Color,
    val tapCount: Int,
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentClickerScreen(
    modifier: Modifier = Modifier,
) {
    var enterTransitionIndex by remember { mutableStateOf(enterTransitions.keys.indexOf("slideIn")) }
    var exitTransitionIndex by remember { mutableStateOf(exitTransitions.keys.indexOf("slideOut")) }
    var oppositeDirections by remember { mutableStateOf(true) }

    var backgroundColor by remember { mutableStateOf(TileColor.list.first()) }
    var tapCounter by remember { mutableStateOf(0) }
    var clickerState by remember { mutableStateOf(ClickerState(backgroundColor, tapCounter)) }

    var observeTap by remember { mutableStateOf(true) }
    var updateColor by remember { mutableStateOf(false) }
    var observeColor by remember { mutableStateOf(false) }

    var moreOptionsExpanded by remember { mutableStateOf(false) }


    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        DropDownWithArrows(
            modifier = Modifier.fillMaxWidth(),
            options = enterTransitions.keys.toList(),
            onSelectionChanged = { enterTransitionIndex = it },
            selectedIndex = enterTransitionIndex,
            label = "Enter:"
        )

        DropDownWithArrows(
            modifier = Modifier.fillMaxWidth(),
            options = exitTransitions.keys.toList(),
            onSelectionChanged = { exitTransitionIndex = it },
            selectedIndex = exitTransitionIndex,
            label = "Exit:"
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Grid.Half)
                .clickable { moreOptionsExpanded = !moreOptionsExpanded },
            text = "More options" + if (moreOptionsExpanded) " ▲" else " ▼",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
        )

        AnimatedVisibility(
            visible = moreOptionsExpanded
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                CheckBoxLabel(
                    text = "Opposite directions",
                    checked = oppositeDirections,
                    onCheckedChange = { oppositeDirections = it }
                )

                CheckBoxLabel(
                    text = "Observe tap",
                    checked = observeTap,
                    onCheckedChange = { observeTap = it }
                )

                CheckBoxLabel(
                    text = "Update color",
                    checked = updateColor,
                    onCheckedChange = { updateColor = it }
                )

                AnimatedVisibility(
                    visible = updateColor,
                ) {
                    CheckBoxLabel(
                        text = "Observe color",
                        checked = observeColor,
                        onCheckedChange = { observeColor = it }
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Grid.Half),
                    onClick = {
                        tapCounter = 0
                        if (observeTap) {
                            clickerState = ClickerState(backgroundColor, tapCounter)
                        }
                    },
                ) {
                    Text("Restart Counter")
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize().background(color = TileColor.Magenta.copy(alpha = 0.2f))) {

            val onTap: () -> Unit = {
                if (updateColor) {
                    backgroundColor = TileColor.list.nextItemLoop(clickerState.color)
                }
                tapCounter = clickerState.tapCount + 1
                clickerState = ClickerState(backgroundColor, tapCounter)
            }
            AnimatedContent(
                modifier = Modifier.align(Alignment.Center),
                targetState = clickerState,
                contentAlignment = Alignment.Center,
                transitionSpec = {
                    if (!oppositeDirections) {
                        enterTransitions.values.toList()[enterTransitionIndex] with // Enter transition
                                exitTransitions.values.toList()[exitTransitionIndex] using // Exit Transition
                                SizeTransform(clip = false)
                    } else {
                        enterTransitions.values.toList()[enterTransitionIndex] with // Enter transition
                                exitTransitionsOpposite.values.toList()[exitTransitionIndex] using
                                SizeTransform(clip = false)
                    }
                },
            ) { state ->
                Clicker(
                    modifier = Modifier.align(Alignment.Center),
                    backgroundColor = if (observeColor) state.color else backgroundColor,
                    tapCounter = if (observeTap) state.tapCount else tapCounter,
                    onTap = onTap,
                )
            }
        }
    }
}

@Composable
fun Clicker(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    tapCounter: Int,
    onTap: () -> Unit,
) {
    Box(
        modifier
            .padding(Grid.Two)
            .size(100.dp)
            // Make it grow
//            .size(100.dp + tapCounter.dp)
            .background(backgroundColor, shape = CircleShape)
            .clip(CircleShape)
            .clickable {
                onTap()
            }

    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = if (tapCounter == 0) "Click me!" else "$tapCounter",
            textAlign = TextAlign.Center,
            style = if (tapCounter == 0) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.headlineMedium,
            color = if (backgroundColor.luminance() > 0.5f) Color.Black else Color.White.copy(alpha = 0.8f),
            fontWeight = if (tapCounter == 0) FontWeight.Normal else FontWeight.Bold,
        )

    }
}
