package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.extensions.nextItemLoop
import nstv.animationshow.common.screen.base.AnimationSpecConfiguration
import nstv.animationshow.common.screen.base.AnimationSpecValues
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.base.defaultFiniteAnimationSpec
import nstv.animationshow.common.screen.base.finiteAnimationSpec
import nstv.animationshow.common.screen.composableApis.CrossfadeScreen.ScreenOne
import nstv.animationshow.common.screen.composableApis.CrossfadeScreen.ScreenTwo


private enum class CrossfadeScreen {
    ScreenOne,
    ScreenTwo,
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun CrossfadeScreen(
    modifier: Modifier = Modifier,
) {
    var screen by remember { mutableStateOf(ScreenOne) }
    var animationSpecIndex by remember { mutableStateOf(0) }
    var useDefaultAnimations by remember { mutableStateOf(false) }
    var animationSpecExpanded by remember { mutableStateOf(false) }
    var animationSpecValues by remember { mutableStateOf(AnimationSpecValues()) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        DropDownWithArrows(
            modifier = modifier.fillMaxWidth(),
            options = finiteAnimationSpec.keys.toList(),
            onSelectionChanged = { animationSpecIndex = it },
            selectedIndex = animationSpecIndex,
            label = "Spec"
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(Grid.One)
                .clickable { animationSpecExpanded = !animationSpecExpanded }
        ) {
            Icon(
                if (animationSpecExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                contentDescription = "Toggle Animation Spec Configuration",
            )
            Text(
                modifier = Modifier.padding(start = Grid.One),
                text = "Configure Animation Spec",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        AnimatedVisibility(
            visible = animationSpecExpanded, modifier = Modifier.padding(Grid.One)
                .background(
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(Grid.One)
                ).padding(Grid.One)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AnimatedVisibility(visible = !useDefaultAnimations) {
                    AnimatedContent(targetState = animationSpecIndex) { targetIndex ->
                        AnimationSpecConfiguration(
                            animationSpecType = finiteAnimationSpec.values.toList()[targetIndex],
                            animationSpecValues = animationSpecValues,
                            onValuesChange = { animationSpecValues = it }
                        )
                    }
                }
                CheckBoxLabel(
                    text = "Use Default Specs",
                    checked = useDefaultAnimations,
                    onCheckedChange = { useDefaultAnimations = it })
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                screen = CrossfadeScreen.values().nextItemLoop(screen)
            }
        ) {
            Text(text = "Click to change screen")
        }
        Crossfade(
            targetState = screen,
            modifier = Modifier.fillMaxWidth(),
            animationSpec = if (useDefaultAnimations) {
                defaultFiniteAnimationSpec.values.toList()[animationSpecIndex]
            } else {
                animationSpecValues.getAnimationSpec(finiteAnimationSpec.values.toList()[animationSpecIndex])
            }
        ) { state ->
            when (state) {
                ScreenOne -> ScreenOne()
                ScreenTwo -> ScreenTwo()
            }
        }
    }
}

@Composable
private fun ScreenOne() {
    ColorScreen(color = TileColor.BlueSlides) {
        Text(
            text = "Screen 1",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
private fun ScreenTwo() {
    ColorScreen(color = TileColor.Orange) {
        Text(
            text = "Screen 2",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
