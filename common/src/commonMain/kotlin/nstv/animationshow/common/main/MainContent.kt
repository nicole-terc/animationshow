package nstv.animationshow.common.main

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nstv.animationshow.common.screen.squareGrid.SquareGrid
import nstv.animationshow.common.design.Grid


private enum class Screen {
    GRID,
}


@Composable
fun MainContent(modifier: Modifier = Modifier) {

    val useDarkIcons = isSystemInDarkTheme().not()
    val surfaceColor = MaterialTheme.colors.surface

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedScreen by remember { mutableStateOf(Screen.GRID) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Grid.Two)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
            ) {
                Row(modifier = Modifier.clickable { expanded = true }) {
                    Text(
                        text = selectedScreen.name,
                        style = MaterialTheme.typography.h6,
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Select Screen"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Screen.values().forEach { screen ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                selectedScreen = screen
                            }
                        ) {
                            Text(text = screen.name)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(vertical = Grid.One))

            Crossfade(
                targetState = selectedScreen,
                animationSpec = tween(durationMillis = 500)
            ) { screen ->
                when (screen) {
                    Screen.GRID -> SquareGrid(numberOfColumns = 10)
                }
            }
        }
    }
}
