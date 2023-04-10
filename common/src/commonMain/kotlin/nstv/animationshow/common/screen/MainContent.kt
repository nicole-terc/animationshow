package nstv.animationshow.common.screen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.design.slidesBackground
import nstv.animationshow.common.screen.Screen.ANIMATION_SPEC_CLICKER
import nstv.animationshow.common.screen.Screen.CONTENT
import nstv.animationshow.common.screen.Screen.CONTENT_CHAOS
import nstv.animationshow.common.screen.Screen.CONTENT_CHAOS_FUN
import nstv.animationshow.common.screen.Screen.CONTENT_CLICKER
import nstv.animationshow.common.screen.Screen.CONTENT_NUMBER
import nstv.animationshow.common.screen.Screen.CONTENT_SIZE
import nstv.animationshow.common.screen.Screen.CONTENT_VISIBILITY
import nstv.animationshow.common.screen.Screen.CROSSFADE
import nstv.animationshow.common.screen.Screen.GRID
import nstv.animationshow.common.screen.Screen.RIPPLE
import nstv.animationshow.common.screen.Screen.VISIBILITY
import nstv.animationshow.common.screen.Screen.VISIBILITY_CHILDREN
import nstv.animationshow.common.screen.Screen.VISIBILITY_TEXT_BOX
import nstv.animationshow.common.screen.composableApis.AnimatedContentClickerScreen
import nstv.animationshow.common.screen.composableApis.AnimatedContentNumberScreen
import nstv.animationshow.common.screen.composableApis.AnimatedContentScreen
import nstv.animationshow.common.screen.composableApis.AnimatedContentSizeScreen
import nstv.animationshow.common.screen.composableApis.AnimationSpecScreen
import nstv.animationshow.common.screen.composableApis.ContentVisibilityScreen
import nstv.animationshow.common.screen.composableApis.CrossfadeScreen
import nstv.animationshow.common.screen.composableApis.VisibilityChildrenScreen
import nstv.animationshow.common.screen.composableApis.VisibilityScreen
import nstv.animationshow.common.screen.composableApis.VisibilityTextBoxScreen
import nstv.animationshow.common.screen.composableApis.chaos.AnimatedContentChaosFunScreen
import nstv.animationshow.common.screen.composableApis.chaos.AnimatedContentChaosScreen
import nstv.animationshow.common.screen.ripple.RippleGrid
import nstv.animationshow.common.screen.squareGrid.SquareGrid


private enum class Screen {
    GRID,
    RIPPLE,
    VISIBILITY,
    VISIBILITY_TEXT_BOX,
    VISIBILITY_CHILDREN,
    CONTENT,
    CONTENT_VISIBILITY,
    CONTENT_SIZE,
    CONTENT_CHAOS,
    CONTENT_CHAOS_FUN,
    CONTENT_CLICKER,
    CONTENT_NUMBER,
    ANIMATION_SPEC_CLICKER,
    CROSSFADE,
}

const val NumberOfColumns = 10
const val UseSlidesBackground = false

@Composable
fun MainContent(modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        var selectedScreen by remember { mutableStateOf(ANIMATION_SPEC_CLICKER) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Grid.Two)
                .background(if (UseSlidesBackground) slidesBackground else Companion.Unspecified)
        ) {
            DropDownWithArrows(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart),
                options = Screen.values().map { it.name }.toList(),
                selectedIndex = Screen.values().indexOf(selectedScreen),
                onSelectionChanged = { selectedScreen = Screen.values()[it] },
                textStyle = MaterialTheme.typography.headlineSmall,
            )
            Divider(modifier = Modifier.fillMaxWidth().padding(vertical = Grid.One))
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
                    VISIBILITY_TEXT_BOX -> VisibilityTextBoxScreen()
                    CONTENT_VISIBILITY -> ContentVisibilityScreen()
                    CONTENT_CHAOS -> AnimatedContentChaosScreen()
                    CONTENT_CHAOS_FUN -> AnimatedContentChaosFunScreen()
                    CONTENT_CLICKER -> AnimatedContentClickerScreen()
                    CONTENT_NUMBER -> AnimatedContentNumberScreen()
                    ANIMATION_SPEC_CLICKER -> AnimationSpecScreen()
                }
            }
        }
    }
}
