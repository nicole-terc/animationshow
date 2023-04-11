package nstv.animationshow.common.screen.composableApis.chaos

import nstv.animationshow.common.screen.base.Piece
import nstv.animationshow.common.screen.base.getRandomPieces
import nstv.animationshow.common.screen.base.piePieces
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Content

sealed interface ChaosUiState {
    object Loading : ChaosUiState
    data class Content(
        val barsPieState: BarsPieState,
    ) : ChaosUiState
}

data class BarsPieState(
    val bars: List<Piece> = emptyList(),
    val pie: List<Piece> = emptyList(),
)

fun getRandomContent(aligned: Boolean = true) =
    if (aligned) {
        getRandomContentAligned()
    } else {
        Content(
            BarsPieState(
                bars = getRandomPieces(),
                pie = piePieces()
            )
        )
    }

fun getRandomContentAligned(): Content = Content(getAlignedBarsPie())

fun getAlignedBarsPie(): BarsPieState {
    val bars = getRandomPieces()
    val barsSum = bars.sumOf { (it.percentage * 100).toInt() }
    val pieParts = bars.map { it.copy(percentage = it.percentage * 100 / barsSum) }

    return BarsPieState(
        bars = bars,
        pie = pieParts
    )
}
