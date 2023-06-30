package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.screen.composableApis.children.getDefaultAnimatedItems


private val columnOptions = listOf(1, 2, 3)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModifierThumblersScreen(
    modifier: Modifier = Modifier,
) {
    var items by remember { mutableStateOf(getDefaultAnimatedItems(6)) }
    var columns by remember { mutableStateOf(2) }
    var showSquares by remember { mutableStateOf(true) }

    Column(modifier = modifier) {

        DropDownWithArrows(
            modifier = Modifier.fillMaxWidth(),
            label = "# Columns",
            selectedIndex = columnOptions.indexOf(columns),
            options = columnOptions.map { it.toString() },
            onSelectionChanged = { columns = columnOptions[it] },
            loopSelection = true
        )
        CheckBoxLabel(
            modifier = Modifier.fillMaxWidth(),
            text = "Appearing squares",
            checked = showSquares,
            onCheckedChange = { showSquares = it }
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = { items = items.shuffled() }) {
            Text(text = "Shuffle items!", color = MaterialTheme.colorScheme.onSecondary)
        }

        LazyVerticalGrid(modifier = Modifier.fillMaxWidth(), columns = GridCells.Fixed(columns)) {
            items(count = items.size, key = { items[it].key }) { index ->
                val item = items[index]
                Box(
                    modifier = Modifier
                        .background(color = item.color)
                        .clickable {
                            val newSize = if (item.size.height == 100.dp) 200.dp else 100.dp
                            items = items.toMutableList().apply {
                                this[index] = item.copy(size = item.size.copy(height = newSize))
                            }
                        }
                        .animateItemPlacement()
                        .animateContentSize(
                            animationSpec = tween(1000)
                        )
                        .height(item.size.height)
                )
            }
        }
    }
}


private fun Color.getInverseColor() = Color(
    red = 1f - red,
    green = 1f - green,
    blue = 1f - blue,
    alpha = alpha
)
