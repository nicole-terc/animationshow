package nstv.animationshow.common.screen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.design.slidesBackground
import nstv.animationshow.common.screen.Screen.*
import nstv.animationshow.common.screen.composableApis.*
import nstv.animationshow.common.screen.composableApis.chaos.AnimatedContentChaosFunScreen
import nstv.animationshow.common.screen.composableApis.chaos.AnimatedContentChaosLookaheadScreen
import nstv.animationshow.common.screen.composableApis.chaos.AnimatedContentChaosScreen
import nstv.animationshow.common.screen.composableApis.children.VisibilityChildrenAdvancedScreen
import nstv.animationshow.common.screen.composableApis.children.VisibilityChildrenScreen
import nstv.animationshow.common.screen.ripple.RippleGrid
import nstv.animationshow.common.screen.squareGrid.SquareGrid


private enum class Screen {
    GRID,
    RIPPLE,
    VISIBILITY,
    VISIBILITY_TEXT_BOX,
    VISIBILITY_CHILDREN,
    CHILDREN_ADVANCED,
    CONTENT,
    CONTENT_VISIBILITY,
    CONTENT_SIZE,
    CONTENT_SIZE_MODIFIER,
    CONTENT_CHAOS,
    CONTENT_CHAOS_FUN,
    CONTENT_CHAOS_LOOK,
    CONTENT_CLICKER,
    CONTENT_NUMBER,
    ANIMATION_SPEC,
    CROSSFADE,
}

const val NumberOfColumns = 10
const val UseSlidesBackground = false

@Composable
fun MainContent(modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        var selectedScreen by remember { mutableStateOf(CONTENT_SIZE_MODIFIER) }

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
                    CONTENT_SIZE_MODIFIER -> ContentSizeModifierScreen()
                    CROSSFADE -> CrossfadeScreen()
                    VISIBILITY_CHILDREN -> VisibilityChildrenScreen()
                    CHILDREN_ADVANCED -> VisibilityChildrenAdvancedScreen()
                    VISIBILITY_TEXT_BOX -> VisibilityTextBoxScreen()
                    CONTENT_VISIBILITY -> ContentVisibilityScreen()
                    CONTENT_CHAOS -> AnimatedContentChaosScreen()
                    CONTENT_CHAOS_FUN -> AnimatedContentChaosFunScreen()
                    CONTENT_CHAOS_LOOK -> AnimatedContentChaosLookaheadScreen()
                    CONTENT_CLICKER -> AnimatedContentClickerScreen()
                    CONTENT_NUMBER -> AnimatedContentNumberScreen()
                    ANIMATION_SPEC -> AnimationSpecScreen()
                }
            }
        }
    }
}
