package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.nextIndexLoop
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.enterTransitions
import nstv.animationshow.common.screen.base.exitTransitions


@Composable
fun VisibilityScreen(
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(true) }
    var enterTransitionIndex by remember { mutableStateOf(0) }
    var exitTransitionIndex by remember { mutableStateOf(0) }
    var colorIndex by remember { mutableStateOf(3) }

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
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                modifier = Modifier.weight(1f),
//                style = MaterialTheme.typography.bodyLarge,
//                text = "Color: "
//            )
//            DropDownWithArrows(
//                modifier = modifier.fillMaxWidth().weight(3f),
//                options = TileColor.map.keys.toList(),
//                onSelectionChanged = { colorIndex = it },
//            )
//        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isVisible = !isVisible
            }
        ) {
            Text(text = "Click to toggle visibility")
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = enterTransitions.values.toList()[enterTransitionIndex],
            exit = exitTransitions.values.toList()[exitTransitionIndex],
        ) {
            ColorScreen(color = TileColor.map.values.toList()[colorIndex],
                modifier = Modifier.fillMaxSize().clickable { colorIndex = TileColor.list.nextIndexLoop(colorIndex) }) {
                Text(
                    text = "I'm visible! :D",
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}
