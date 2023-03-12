package nstv.animationshow.common.screen.tile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor

data class Tile(
    val color: Color,
)

object TileList {
    fun simple(size: Int, color: Color = TileColor.Blue) = List(size) { Tile(color) }
    fun withRandomColors(size: Int) = List(size) { Tile(TileColor.random()) }
    fun withUniformColors(size: Int) = List(size) { Tile(TileColor.list[it.mod(TileColor.list.size)]) }

}


@Composable
fun TileSimpleSquare(
    tile: Tile,
    modifier: Modifier = Modifier,
    borderRadius: Dp = Grid.Half,
) {
    Box(
        modifier = modifier
            .background(shape = RoundedCornerShape(size = borderRadius), color = tile.color)
    )
}
