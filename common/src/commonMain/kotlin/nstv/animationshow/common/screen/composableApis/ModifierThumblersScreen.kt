package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
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
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
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
import nstv.animationshow.common.screen.base.getThumbler
import nstv.animationshow.common.screen.base.getThumblerList


private val columnOptions = listOf(1, 2, 3, 4)

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun ModifierThumblersScreen(
    modifier: Modifier = Modifier,
) {
    var items by remember { mutableStateOf(getThumblerList(6)) }
    val inceptionItems by remember { mutableStateOf(getThumblerList(5).filter { it.id != 2 }) }

    var columns by remember { mutableStateOf(2) }
    var showChildren by remember { mutableStateOf(true) }
    var independentChildren by remember { mutableStateOf(true) }
    var moreOptionsExpanded by remember { mutableStateOf(false) }

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
                onClick = { items = items.toMutableList().apply { removeIf { it.id == items.size - 1 } } }
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
                    items = items.toMutableList().apply { add(index = 0, getThumbler(items.size)) }
                }
            ) {
                Text(text = "+", color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }

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
                    modifier = Modifier.fillMaxWidth(),
                    text = "Show Children",
                    checked = showChildren,
                    onCheckedChange = { showChildren = it },
                    textStyle = MaterialTheme.typography.bodyMedium,
                )

                AnimatedVisibility(
                    visible = showChildren,
                    enter = slideInHorizontally(initialOffsetX = { it }),
                    exit = fadeOut(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CheckBoxLabel(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Independent Children",
                        checked = independentChildren,
                        onCheckedChange = { independentChildren = it },
                        textStyle = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        Button(modifier = Modifier.fillMaxWidth(), onClick = { items = items.shuffled() }) {
            Text(text = "Shuffle items!")
        }

        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(columns)) {
            items(count = items.size, key = { items[it].id }) { index ->
                val item = items[index]
                // Thumbler View
                Column(
                    modifier = Modifier
                        .padding(Grid.Half)
                        .background(color = item.color, shape = RoundedCornerShape(Grid.Half))
                        .clickable {
                            items = items.toMutableList().apply {
                                this[index] = item.copy(expanded = !item.expanded, showChildren = false)
                            }
                        }
                        // NicNote: Needs to be inside a LazyLayout to work
                        .animateItemPlacement(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        )
                        // NicNote: Needs to be before size modifiers, but after any other graphic layer modifiers
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
                    if (showChildren) {
                        AnimatedVisibility(
                            visible = item.showChildren,
                            enter = if (independentChildren) EnterTransition.None else fadeIn() + expandVertically(),
                            exit = if (independentChildren) ExitTransition.None else fadeOut() + shrinkVertically(),
                        ) {
                            Column(modifier = Modifier.fillMaxSize().padding(Grid.One)) {
                                inceptionItems.forEach { childItem ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .padding(Grid.Half)
                                            .animateEnterExit(
                                                enter = if (independentChildren) fadeIn() + expandIn() else EnterTransition.None,
                                                exit = if (independentChildren) fadeOut() + shrinkOut() else ExitTransition.None,
                                                label = childItem.label
                                            )
                                            .background(
                                                color = item.color.getInverseColor().copy(alpha = 0.8f),
                                                shape = RoundedCornerShape(Grid.Quarter),
                                            )

                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
