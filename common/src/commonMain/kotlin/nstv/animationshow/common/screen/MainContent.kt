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
import nstv.animationshow.common.screen.Screen.ANIMATION_SPEC_TWEENS
import nstv.animationshow.common.screen.Screen.CONDUCTOR_CROSSFADE
import nstv.animationshow.common.screen.Screen.CURTAIN_ANIM_VISIBILITY
import nstv.animationshow.common.screen.Screen.EXAMPLE_SIZE_CHANGER
import nstv.animationshow.common.screen.Screen.EXTRA_BUBBLES
import nstv.animationshow.common.screen.Screen.EXTRA_GRAPHS
import nstv.animationshow.common.screen.Screen.EXTRA_HAPPY_BARS
import nstv.animationshow.common.screen.Screen.EXTRA_TWEENS
import nstv.animationshow.common.screen.Screen.NO_SHOW_CONTENT_SWAP
import nstv.animationshow.common.screen.Screen.NO_SHOW_CURTAIN_V2
import nstv.animationshow.common.screen.Screen.NO_SHOW_GRAPHS_BORING
import nstv.animationshow.common.screen.Screen.NO_SHOW_GRID
import nstv.animationshow.common.screen.Screen.NO_SHOW_RIPPLE
import nstv.animationshow.common.screen.Screen.P_0_CURTAIN
import nstv.animationshow.common.screen.Screen.P_1_CLICKER
import nstv.animationshow.common.screen.Screen.P_2_SIZE_SHIFTER
import nstv.animationshow.common.screen.Screen.P_3_MODIFIER_THUMBLERS
import nstv.animationshow.common.screen.Screen.WIP_CHILDREN_ADVANCED
import nstv.animationshow.common.screen.Screen.WIP_GRAPHS_LOOKAHEAD
import nstv.animationshow.common.screen.composableApis.AnimatedContentClickerScreen
import nstv.animationshow.common.screen.composableApis.AnimatedContentNumberScreen
import nstv.animationshow.common.screen.composableApis.AnimatedContentScreen
import nstv.animationshow.common.screen.composableApis.AnimatedContentSizeShifter
import nstv.animationshow.common.screen.composableApis.AnimationSpecScreen
import nstv.animationshow.common.screen.composableApis.ContentSizeModifierScreen
import nstv.animationshow.common.screen.composableApis.ContentVisibilityScreen
import nstv.animationshow.common.screen.composableApis.CrossfadeScreen
import nstv.animationshow.common.screen.composableApis.ModifierThumblersScreen
import nstv.animationshow.common.screen.composableApis.VisibilityScreen
import nstv.animationshow.common.screen.composableApis.VisibilityTextBoxScreen
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
