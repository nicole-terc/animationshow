package nstv.animationshow.common.screen.squareGrid

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
import nstv.animationshow.common.screen.base.Tile
import nstv.animationshow.common.screen.base.TileList
import nstv.animationshow.common.screen.base.TileSimpleSquare


@Composable
fun SquareGrid(
    modifier: Modifier = Modifier,
    numberOfColumns: Int = 10,
    items: List<Tile> = TileList.simple(numberOfColumns * numberOfColumns),
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var elementSize = size.toDpSizeSquared() / numberOfColumns

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


