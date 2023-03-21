package nstv.animationshow.common.screen.base

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
    val frontColor: Color,
    val backColor: Color = frontColor,
)

object TileList {
    fun simple(size: Int, frontColor: Color = TileColor.Blue, backColor: Color = TileColor.Orange) =
        List(size) { Tile(frontColor = frontColor, backColor = backColor) }

    fun withRandomColors(size: Int) = List(size) {
        Tile(
            frontColor = TileColor.random(),
            backColor = TileColor.random()
        )
    }

    fun withUniformColors(size: Int) = List(size) {
        Tile(
            frontColor = TileColor.list[it.mod(TileColor.list.size)],
            backColor = TileColor.list[TileColor.list.size - 1 - it.mod(TileColor.list.size)]
        )
    }

    fun grid(
        columns: Int,
        frontColor: Color = TileColor.Blue,
        backColor: Color = TileColor.Orange
    ): List<List<Tile>> =
        List(columns) { List(columns) { Tile(frontColor = frontColor, backColor = backColor) } }
}

@Composable
fun TileSimpleSquare(
    color: Color,
    modifier: Modifier = Modifier,
    borderRadius: Dp = Grid.Half,
) {
    TileSimpleSquare(Tile(color), modifier, borderRadius)
}

@Composable
fun TileSimpleSquare(
    tile: Tile,
    modifier: Modifier = Modifier,
    borderRadius: Dp = Grid.Half,
) {
    Box(
        modifier = modifier
            .background(shape = RoundedCornerShape(size = borderRadius), color = tile.frontColor)
    )
}
