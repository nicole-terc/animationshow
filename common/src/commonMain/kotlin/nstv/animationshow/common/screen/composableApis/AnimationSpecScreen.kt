package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel
import nstv.animationshow.common.design.components.DropDownWithArrows
import nstv.animationshow.common.screen.base.AnimationSpecConfiguration
import nstv.animationshow.common.screen.base.AnimationSpecValues
import nstv.animationshow.common.screen.base.finiteAnimationSpec


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationSpecScreen(
    modifier: Modifier = Modifier,
) {
    var tapCounter by remember { mutableStateOf(0) }
    var animationSpecIndex by remember { mutableStateOf(0) }
    var useDefaultAnimations by remember { mutableStateOf(false) }
    var animationSpecExpanded by remember { mutableStateOf(false) }
    var animationSpecValues by remember { mutableStateOf(AnimationSpecValues()) }
    var emulateVisibilityChange by remember { mutableStateOf(true) }
    var useAnimationSpecInExitTransition by remember { mutableStateOf(true) }


    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        DropDownWithArrows(
            modifier = modifier.fillMaxWidth(),
            options = finiteAnimationSpec.keys.toList(),
            onSelectionChanged = { animationSpecIndex = it },
            selectedIndex = animationSpecIndex,
            label = "Spec:"
        )

        CheckBoxLabel(
            text = "Emulate Visibility Change",
            checked = emulateVisibilityChange,
            onCheckedChange = { emulateVisibilityChange = it }
        )

        CheckBoxLabel(
            text = "Use Animation Spec in Exit",
            checked = useAnimationSpecInExitTransition,
            onCheckedChange = { useAnimationSpecInExitTransition = it }
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = Grid.One)
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
            onClick = { tapCounter++ },
            content = {
                Text(text = "Change content")
            })

        Box(modifier = Modifier.fillMaxSize().background(color = TileColor.Purple.copy(alpha = 0.5f))) {
            val colorOne = TileColor.Yellow
            val colorTwo = if (emulateVisibilityChange) Color.Transparent else TileColor.Blue
            val animationSpec =
                animationSpecValues.getAnimationSpecIntOffset(finiteAnimationSpec.values.toList()[animationSpecIndex])
            AnimatedContent(
                modifier = Modifier.align(Alignment.Center),
                targetState = tapCounter,
                contentAlignment = Alignment.Center,
                transitionSpec = {
                    slideInHorizontally(animationSpec = animationSpec) { -it * 3 } with // Enter transition
                            slideOutHorizontally(
                                animationSpec = if (useAnimationSpecInExitTransition) animationSpec else spring(
                                    stiffness = Spring.StiffnessMediumLow,
                                    visibilityThreshold = IntOffset.VisibilityThreshold
                                )
                            ) { it * 3 } using SizeTransform(clip = false)
                },
            ) { state ->
                Box(
                    modifier
                        .padding(Grid.Two)
                        .align(Alignment.Center)
                        .size(200.dp)
                        .background(
                            if (state % 2 == 0) colorOne else colorTwo,
                            shape = RoundedCornerShape(Grid.One)
                        )
                        .clip(RoundedCornerShape(Grid.One))
                        .clickable {
                            tapCounter++
                        }

                )
            }
        }
    }
}
