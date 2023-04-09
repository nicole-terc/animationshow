package nstv.animationshow.common.screen.base

import androidx.compose.ui.graphics.Color
import kotlin.random.Random
import nstv.animationshow.common.design.TileColor

data class Piece(
    val id: Int,
    val percentage: Float,
    val color: Color
)

fun piePieces(): List<Piece> {
    val pieces = mutableListOf<Piece>()
    var currentPercentage = 0f

    while (currentPercentage < 1f) {
        var nextPercentage = Random.nextDouble(0.1, 0.3).toFloat()

        if (currentPercentage + nextPercentage > 1f) {
            nextPercentage = 1f - currentPercentage
        }

        pieces.add(
            Piece(
                id = pieces.size,
                percentage = nextPercentage,
                color = TileColor.list[pieces.size]
            )
        )
        currentPercentage += nextPercentage
    }
    return pieces
}
