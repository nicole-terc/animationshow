package nstv.animationshow.common.screen

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
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.screen.Screen.CONTENT
import nstv.animationshow.common.screen.Screen.CONTENT_SIZE
import nstv.animationshow.common.screen.Screen.CROSSFADE
import nstv.animationshow.common.screen.Screen.GRID
import nstv.animationshow.common.screen.Screen.RIPPLE
import nstv.animationshow.common.screen.Screen.VISIBILITY
import nstv.animationshow.common.screen.Screen.VISIBILITY_CHILDREN
import nstv.animationshow.common.screen.composableApis.AnimatedContentScreen
import nstv.animationshow.common.screen.composableApis.AnimatedContentSizeScreen
import nstv.animationshow.common.screen.composableApis.CrossfadeScreen
import nstv.animationshow.common.screen.composableApis.VisibilityChildrenScreen
import nstv.animationshow.common.screen.ripple.RippleGrid
import nstv.animationshow.common.screen.squareGrid.SquareGrid
import nstv.animationshow.common.screen.composableApis.VisibilityScreen


private enum class Screen {
    GRID,
    RIPPLE,
    VISIBILITY,
    VISIBILITY_CHILDREN,
    CONTENT,
    CONTENT_SIZE,
    CROSSFADE,
}

const val NumberOfColumns = 10

@Composable
fun MainContent(modifier: Modifier = Modifier) {

    val useDarkIcons = isSystemInDarkTheme().not()
    val surfaceColor = MaterialTheme.colorScheme.surface

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedScreen by remember { mutableStateOf(CROSSFADE) }

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
                        style = MaterialTheme.typography.headlineSmall,
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
                    GRID -> SquareGrid(numberOfColumns = NumberOfColumns)
                    RIPPLE -> RippleGrid(numberOfColumns = NumberOfColumns)
                    VISIBILITY -> VisibilityScreen()
                    CONTENT -> AnimatedContentScreen()
                    CONTENT_SIZE -> AnimatedContentSizeScreen()
                    CROSSFADE -> CrossfadeScreen()
                    VISIBILITY_CHILDREN -> VisibilityChildrenScreen()
                }
            }
        }
    }
}
