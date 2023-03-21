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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import java.lang.Integer.max
import java.lang.Integer.min

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DropDownWithArrows(
    options: List<String>,
    onSelectionChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(0) }
    var fromDropdown by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Row(modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Option",
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = {
                        fromDropdown = false
                        selectedItemIndex = max(selectedItemIndex - 1, 0)
                        onSelectionChanged(selectedItemIndex)
                    })
            )
            AnimatedContent(
                modifier = Modifier.weight(4f),
                targetState = selectedItemIndex,
                transitionSpec = {
                    if (fromDropdown) {
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    } else if (targetState > initialState) {
                        slideInHorizontally { width -> width } + fadeIn() with
                                slideOutHorizontally { width -> -width } + fadeOut()
                    } else {
                        slideInHorizontally { width -> -width } + fadeIn() with
                                slideOutHorizontally { width -> width } + fadeOut()
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
                        fromDropdown = false
                        selectedItemIndex = min(selectedItemIndex + 1, options.size - 1)
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
                        fromDropdown = true
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
