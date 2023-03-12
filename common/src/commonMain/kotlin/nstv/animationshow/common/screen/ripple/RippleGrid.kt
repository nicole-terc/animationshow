package nstv.animationshow.common.screen.ripple

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.extensions.toDpSizeSquared
import nstv.animationshow.common.screen.tile.Tile
import nstv.animationshow.common.screen.tile.TileList
import nstv.animationshow.common.screen.tile.TileSimpleSquare

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RippleGrid(
    numberOfColumns: Int,
    modifier: Modifier = Modifier,
    items: List<Tile> = TileList.simple(numberOfColumns * numberOfColumns),
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var elementSize = size.toDpSizeSquared() / numberOfColumns
    var tileState = remember { mutableStateOf(items.map { RippleState.Idle }) }

    AnimatedContent(targetState = tileState.value) { tileState ->
        Column(modifier = modifier.fillMaxSize().onGloballyPositioned {
            size = it.size
        }) {
            for (i in 0 until numberOfColumns) {
                Row(modifier = modifier) {
                    items.subList(i * numberOfColumns, (i + 1) * numberOfColumns).forEach { tile ->
                        TileSimpleSquare(tile, Modifier.size(elementSize).padding(Grid.Half))
                    }
                }
            }
        }
    }
}

enum class RippleState {
    Idle,
    RippleStart,
    RippleEnd,
}
