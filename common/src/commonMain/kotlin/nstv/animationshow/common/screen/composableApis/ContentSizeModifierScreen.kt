package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.design.components.CheckBoxLabel

@Composable
fun ContentSizeModifierScreen(
    modifier: Modifier = Modifier,
) {
    var scale by remember { mutableStateOf(1f) }
    var shouldScale by remember { mutableStateOf(true) }

    Column(modifier) {
        CheckBoxLabel(
            "Should Scale",
            checked = shouldScale,
            onCheckedChange = { shouldScale = it }
        )
        Button(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            onClick = {
                if (shouldScale) {
                    scale = if (scale == 1f) 0.5f else 1f
                }
            }
        ) {
            Text(text = "Change!")
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        color = if (scale == 1f) TileColor.BlueSlides else TileColor.PurpleSlides,
                        shape = RoundedCornerShape(Grid.One)
                    )
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    .fillMaxWidth(0.5f * scale)
                    .aspectRatio(1f)
                //Nic Note: Scale doesn't work with animateContentSize, not even if width and height are set vs aspectRatio
//                    .scale(scale)
                // Nic note: If background is put after animateContentSize, the size change animation is anchored to the
                // top left before moving to the center again.
//                    .background(
//                        color = if (scale == 1f) TileColor.Blue else TileColor.Purple,
//                        shape = RoundedCornerShape(Grid.One)
//                    )
            )
        }
    }

}
