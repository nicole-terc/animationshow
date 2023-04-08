package nstv.animationshow.common.screen.composableApis

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
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
import nstv.animationshow.common.extensions.nextItemLoop
import nstv.animationshow.common.screen.base.ColorScreen
import nstv.animationshow.common.screen.composableApis.CrossfadeSubScreen.ColorSubScreenOne
import nstv.animationshow.common.screen.composableApis.CrossfadeSubScreen.ColorSubScreenTwo
import nstv.animationshow.common.screen.defaultFiniteAnimationSpec
import nstv.animationshow.common.screen.finiteAnimationSpec


private enum class CrossfadeSubScreen {
    ColorSubScreenOne,
    ColorSubScreenTwo,
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CrossfadeScreen(
    modifier: Modifier = Modifier,
) {
    var currentSubScreen by remember { mutableStateOf(ColorSubScreenOne) }
    var animationSpecIndex by remember { mutableStateOf(0) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                text = "Animation Spec: "
            )
            DropDownWithArrows(
                modifier = modifier.fillMaxWidth().weight(3f),
                options = finiteAnimationSpec.keys.toList(),
                onSelectionChanged = { animationSpecIndex = it },
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                currentSubScreen = CrossfadeSubScreen.values().nextItemLoop(currentSubScreen)
            }
        ) {
            Text(text = "Click to change screen")
        }
        Crossfade(
            targetState = currentSubScreen,
            modifier = Modifier.fillMaxWidth(),
            animationSpec = defaultFiniteAnimationSpec.values.toList()[animationSpecIndex]
        ) {
            when (it) {
                ColorSubScreenOne -> ColorScreen(color = TileColor.Blue)
                ColorSubScreenTwo -> ColorScreen(color = TileColor.Orange)
            }
        }
    }
}
