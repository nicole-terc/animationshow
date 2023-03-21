package nstv.animationshow.common.screen.ripple

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.extensions.toDpSizeSquared
import nstv.animationshow.common.screen.base.Tile
import nstv.animationshow.common.screen.base.TileList
import nstv.animationshow.common.screen.base.TileSimpleSquare
import nstv.animationshow.common.screen.ripple.RippleState.Idle
import nstv.animationshow.common.screen.ripple.RippleState.RippleEnd
import nstv.animationshow.common.screen.ripple.RippleState.RippleStart

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RippleGrid(
    numberOfColumns: Int,
    modifier: Modifier = Modifier,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var elementSize = size.toDpSizeSquared() / numberOfColumns

    val tileGrid by remember { mutableStateOf(TileList.grid(numberOfColumns)) }
    var tileState = remember { mutableStateOf(tileGrid.map { it.map { Idle } }) }

    var currentRippleDistance by remember { mutableStateOf(0) }

    Column(modifier = modifier.fillMaxSize().onGloballyPositioned {
        size = it.size
    }) {
        AnimatedContent(targetState = tileState.value, transitionSpec = {
            fadeIn(animationSpec = tween(0, delayMillis = 0)) with
                    fadeOut(animationSpec = tween(0))
        }) { tileState ->
            Column(modifier = Modifier.wrapContentHeight()) {
                tileGrid.forEachIndexed { rowIndex, row ->
                    Row {
                        row.forEachIndexed { columnIndex, tile ->
                            TileRippleSquare(
                                tile = tile,
                                rippleState = tileState[rowIndex][columnIndex],
                                modifier = Modifier.size(elementSize).padding(Grid.Half)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                tileState.value = tileState.value.ripple(
                    rippleStartPoint = IntOffset(0, 0),
                    currentRippleDistance = currentRippleDistance
                )
                if (currentRippleDistance == tileGrid.size) {
                    currentRippleDistance = 0
                } else {
                    currentRippleDistance++
                }
            }) {
            Text("Ripple")
        }

    }
}

fun List<List<RippleState>>.ripple(
    rippleStartPoint: IntOffset,
    currentRippleDistance: Int,
) = mapIndexed { row, rowArray ->
    rowArray.mapIndexed { column, state ->
        if (this[row][column] == RippleStart || this[row][column] == RippleEnd) {
            RippleEnd
        } else if (row > rippleStartPoint.y - currentRippleDistance && row < rippleStartPoint.y + currentRippleDistance &&
            column > rippleStartPoint.x - currentRippleDistance && column < rippleStartPoint.x + currentRippleDistance
        ) {
            RippleStart
        } else {
            Idle
        }
    }
}

@Composable
fun TileRippleSquare(
    tile: Tile,
    rippleState: RippleState,
    modifier: Modifier = Modifier,
) {
    when (rippleState) {
        Idle -> TileSimpleSquare(tile, modifier)
        RippleStart -> TileSimpleSquare(TileColor.Red, modifier)
        RippleEnd -> TileSimpleSquare(tile.backColor, modifier)
    }
}

enum class RippleState {
    Idle,
    RippleStart,
    RippleEnd,
}
