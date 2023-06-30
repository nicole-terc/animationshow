package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.getInverseColor
import nstv.animationshow.common.screen.composableApis.children.getDefaultAnimatedItem
import nstv.animationshow.common.screen.composableApis.children.getDefaultAnimatedItems


private val columnOptions = listOf(1, 2, 3)

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun ModifierThumblersScreen(
    modifier: Modifier = Modifier,
) {
    var items by remember { mutableStateOf(getDefaultAnimatedItems(6)) }
    var columns by remember { mutableStateOf(2) }
    var independentChildren by remember { mutableStateOf(true) }
    val inceptionItems by remember { mutableStateOf(getDefaultAnimatedItems(5).filter { it.key != 2 }) }

    Column(modifier = modifier) {
        DropDownWithArrows(
            modifier = Modifier.fillMaxWidth(),
            label = "# Columns",
            selectedIndex = columnOptions.indexOf(columns),
            options = columnOptions.map { it.toString() },
            onSelectionChanged = { columns = columnOptions[it] },
            loopSelection = true
        )

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1.5f),
                text = "# Items",
                style = MaterialTheme.typography.bodyLarge,
            )
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.secondaryContainer),
                onClick = { items = items.toMutableList().apply { removeIf { it.key == items.size - 1 } } }
            ) {
                Text(text = "-", color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
            Text(
                modifier = Modifier.weight(0.5f),
                text = "${items.size}",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
            )
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.secondaryContainer),
                onClick = {
                    items = items.toMutableList().apply { add(getDefaultAnimatedItem(items.size)) }
                }
            ) {
                Text(text = "+", color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }

        CheckBoxLabel(
            modifier = Modifier.fillMaxWidth(),
            text = "Independent Children",
            checked = independentChildren,
            onCheckedChange = { independentChildren = it }
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = { items = items.shuffled() }) {
            Text(text = "Shuffle items!", color = MaterialTheme.colorScheme.onSecondary)
        }

        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(columns)) {
            items(count = items.size, key = { items[it].key }) { index ->
                val item = items[index]
                Column(
                    modifier = Modifier
                        .padding(Grid.Half)
                        .background(color = item.color, shape = RoundedCornerShape(Grid.Half))
                        .clickable {
                            items = items.toMutableList().apply {
                                this[index] = item.copy(expanded = !item.expanded, showChildren = false)
                            }
                        }
                        .animateItemPlacement(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        )
                        .animateContentSize(
                            finishedListener = { initialValue, targetValue ->
                                if (targetValue.height > initialValue.height) {
                                    items = items.toMutableList().apply {
                                        this[index] = item.copy(showChildren = true)
                                    }
                                }
                            }
                        )
                        .height(if (item.expanded) 200.dp else 100.dp)
                ) {
                    AnimatedContent(
                        targetState = item.showChildren,
                        transitionSpec = {
                            if (independentChildren) {
                                EnterTransition.None with ExitTransition.None
                            } else {
                                slideInHorizontally(spring(dampingRatio = Spring.DampingRatioMediumBouncy)) with fadeOut()
                            }
                        }
                    ) { show ->
                        if (show) {
                            Column(modifier = Modifier.fillMaxSize().padding(Grid.One)) {
                                inceptionItems.forEach { childItem ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .padding(Grid.Half)
                                            .animateEnterExit(
                                                enter = if (independentChildren) childItem.enterTransition else EnterTransition.None,
                                                exit = if (independentChildren) childItem.exitTransition else ExitTransition.None,
                                                label = childItem.label
                                            )
                                            .background(
                                                color = item.color.getInverseColor().copy(alpha = 0.8f),
                                                shape = RoundedCornerShape(Grid.Quarter),
                                            )

                                    )
                                }
                            }
                        } else {
                            Column(Modifier.fillMaxSize()) { }
                        }
                    }
                }
            }
        }
    }
}
