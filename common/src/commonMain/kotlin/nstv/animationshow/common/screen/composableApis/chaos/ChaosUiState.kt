package nstv.animationshow.common.screen.composableApis.chaos

import androidx.compose.ui.graphics.Color
import nstv.animationshow.common.design.TileColor
import nstv.animationshow.common.screen.base.Piece
import nstv.animationshow.common.screen.base.getRandomPieces
import nstv.animationshow.common.screen.base.piePieces
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Content

sealed interface ChaosUiState {
    object Loading : ChaosUiState
    data class Content(
        val barsPieState: BarsPieState,
        val bottomBar: BottomBar?,
    ) : ChaosUiState
}

data class BottomBar(
    val label: String,
    val color: Color,
)

data class BarsPieState(
    val bars: List<Piece> = emptyList(),
    val pie: List<Piece> = emptyList(),
)

fun getRandomContent(aligned: Boolean = true, withBottomBar: Boolean = true) =
    Content(
        barsPieState = if (aligned) getAlignedBarsPie() else {
            BarsPieState(
                bars = getRandomPieces(),
                pie = piePieces()
            )
        },
        bottomBar = if (withBottomBar) defaultBottomBar() else null
    )


fun getAlignedBarsPie(): BarsPieState {
    val bars = getRandomPieces()
    val barsSum = bars.sumOf { (it.percentage * 100).toInt() }
    val pieParts = bars.map { it.copy(percentage = it.percentage * 100 / barsSum) }

    return BarsPieState(
        bars = bars,
        pie = pieParts
    )
}

fun defaultBottomBar() = BottomBar(label = "Bottom Bar Label", color = TileColor.Purple)

