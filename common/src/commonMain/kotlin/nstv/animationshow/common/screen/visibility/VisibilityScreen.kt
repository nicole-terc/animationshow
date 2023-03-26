package nstv.animationshow.common.screen.visibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.enterTransitions
import nstv.animationshow.common.screen.exitTransitions


@Composable
fun VisibilityScreen(
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(true) }
    var enterTransitionIndex by remember { mutableStateOf(0) }
    var exitTransitionIndex by remember { mutableStateOf(0) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                text = "Enter Transition: "
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
                text = "Exit Transition: "
            )
            DropDownWithArrows(
                modifier = modifier.fillMaxWidth().weight(3f),
                options = exitTransitions.keys.toList(),
                onSelectionChanged = { exitTransitionIndex = it },
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                visible = !visible
            }
        ) {
            Text(text = "Click to toggle visibility")
        }
        AnimatedVisibility(
            visible = visible,
            modifier = Modifier,
            enter = enterTransitions.values.toList()[enterTransitionIndex],
            exit = exitTransitions.values.toList()[exitTransitionIndex],
        ) {
            ColorScreen(color = TileColor.Blue) {
                Text("I'm visible! :D")
            }
        }
    }
}
