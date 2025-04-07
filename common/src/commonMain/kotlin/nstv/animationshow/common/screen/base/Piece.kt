package nstv.animationshow.common.screen.base

import androidx.compose.ui.graphics.Color
import nstv.animationshow.common.design.TileColor
import kotlin.random.Random

data class Piece(
    val id: Int,
    val percentage: Float,
    val color: Color
)

fun getRandomPieces(size: Int = 5) = List(size) {
    Piece(
        it + 1,
        Random.nextFloat().coerceAtLeast(0.1f),
        color = TileColor.list[it + 1]
    )
}

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
