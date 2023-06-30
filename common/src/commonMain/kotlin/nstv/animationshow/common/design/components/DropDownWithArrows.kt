package nstv.animationshow.common.design.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import java.lang.Integer.max
import java.lang.Integer.min


private enum class Direction {
    TO_LEFT, TO_RIGHT, FROM_DROPDOWN
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DropDownWithArrows(
    options: List<String>,
    onSelectionChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    label: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    loopSelection: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(selectedIndex) }
    var direction by remember { mutableStateOf(Direction.FROM_DROPDOWN) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Row(modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }.height(Max)) {
            if (label != null) {
                Text(
                    modifier = Modifier.weight(2f),
                    style = textStyle,
                    text = label
                )
            }
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Option",
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = {
                        direction = Direction.TO_LEFT
                        selectedItemIndex = if (loopSelection && selectedItemIndex == 0) {
                            options.size - 1
                        } else {
                            max(selectedItemIndex - 1, 0)
                        }
                        onSelectionChanged(selectedItemIndex)
                    })
            )
            AnimatedContent(
                modifier = Modifier.weight(4f),
                targetState = selectedItemIndex,
                transitionSpec = {
                    when (direction) {
                        Direction.FROM_DROPDOWN -> slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()

                        Direction.TO_LEFT -> slideInHorizontally { width -> -width / 2 } + fadeIn() with
                                slideOutHorizontally { width -> width / 2 } + fadeOut()

                        Direction.TO_RIGHT -> slideInHorizontally { width -> width / 2 } + fadeIn() with
                                slideOutHorizontally { width -> -width / 2 } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = options[selectedItemIndex],
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Option",
                Modifier
                    .weight(1f)
                    .clickable(onClick = {
                        direction = Direction.TO_RIGHT

                        selectedItemIndex =
                            if (loopSelection && selectedItemIndex == options.size - 1) {
                                0
                            } else {
                                min(selectedItemIndex + 1, options.size - 1)
                            }

                        onSelectionChanged(selectedItemIndex)
                    })
            )
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        direction = Direction.FROM_DROPDOWN
                        selectedItemIndex = options.indexOf(option)
                        onSelectionChanged(options.indexOf(option))
                    }
                ) {
                    Text(
                        text = option,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
