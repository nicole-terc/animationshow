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
    EXAMPLE_SIZE_CHANGER,
    CURTAIN_ANIM_VISIBILITY,
    ANIMATION_SPEC_TWEENS,
    CONDUCTOR_CROSSFADE,
    P_0_CURTAIN,
    P_1_CLICKER,
    P_2_SIZE_SHIFTER,
    P_3_MODIFIER_THUMBLERS,
    EXTRA_TWEENS,
    EXTRA_BUBBLES,
    EXTRA_HAPPY_BARS,
    EXTRA_GRAPHS,
    NO_SHOW_GRID,
    NO_SHOW_RIPPLE,
    NO_SHOW_CONTENT_SWAP,
    NO_SHOW_CURTAIN_V2,
    NO_SHOW_GRAPHS_BORING,
    WIP_CHILDREN_ADVANCED,
    WIP_GRAPHS_LOOKAHEAD,
}

const val NumberOfColumns = 10
const val UseSlidesBackground = false

@Composable
fun MainContent(modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        var selectedScreen by remember { mutableStateOf(P_3_MODIFIER_THUMBLERS) }

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
                loopSelection = true,
            )
            Divider(modifier = Modifier.fillMaxWidth().padding(vertical = Grid.One))
            Crossfade(
                targetState = selectedScreen,
                animationSpec = tween(durationMillis = 500)
            ) { screen ->
                when (screen) {
                    EXAMPLE_SIZE_CHANGER -> ContentSizeModifierScreen()
                    CURTAIN_ANIM_VISIBILITY -> VisibilityScreen()
                    ANIMATION_SPEC_TWEENS -> AnimationSpecScreen()
                    CONDUCTOR_CROSSFADE -> CrossfadeScreen()
                    P_0_CURTAIN -> ContentVisibilityScreen()
                    P_1_CLICKER -> AnimatedContentClickerScreen()
                    P_2_SIZE_SHIFTER -> AnimatedContentSizeShifter()
                    P_3_MODIFIER_THUMBLERS -> ModifierThumblersScreen()
                    EXTRA_TWEENS -> AnimationSpecScreen()
                    EXTRA_BUBBLES -> AnimatedContentNumberScreen()
                    EXTRA_HAPPY_BARS -> VisibilityChildrenScreen()
                    EXTRA_GRAPHS -> AnimatedContentChaosFunScreen()
                    NO_SHOW_CURTAIN_V2 -> VisibilityTextBoxScreen()
                    NO_SHOW_GRAPHS_BORING -> AnimatedContentChaosScreen()
                    NO_SHOW_GRID -> SquareGrid(numberOfColumns = NumberOfColumns)
                    NO_SHOW_RIPPLE -> RippleGrid(numberOfColumns = NumberOfColumns)
                    NO_SHOW_CONTENT_SWAP -> AnimatedContentScreen()
                    WIP_CHILDREN_ADVANCED -> VisibilityChildrenAdvancedScreen() // Same as VisibilityChildrenScreen() for now
                    WIP_GRAPHS_LOOKAHEAD -> AnimatedContentChaosLookaheadScreen() // Does Nothing atm
                }
            }
        }
    }
}
