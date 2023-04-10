package nstv.animationshow.common.screen.composableApis.chaos

import nstv.animationshow.common.screen.base.Piece
import nstv.animationshow.common.screen.base.getRandomPieces
import nstv.animationshow.common.screen.base.piePieces
import nstv.animationshow.common.screen.composableApis.chaos.ChaosUiState.Content

sealed interface ChaosUiState {
    object Loading : ChaosUiState
    data class Content(
        val bars: List<Piece> = emptyList(),
        val pie: List<Piece> = emptyList(),
    ) : ChaosUiState
}

fun getRandomContent(aligned: Boolean = true) =
    if (aligned) {
        getRandomContentAligned()
    } else {
        Content(
            bars = getRandomPieces(),
            pie = piePieces()
        )
    }

fun getRandomContentAligned(): Content {
    val bars = getRandomPieces()
    val barsSum = bars.sumOf { (it.percentage * 100).toInt() }
    val pieParts = bars.map { it.copy(percentage = it.percentage * 100 / barsSum) }

    return Content(
        bars = bars,
        pie = pieParts
    )
}
