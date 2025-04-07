package nstv.animationshow.common.screen.composableApis.chaos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Fill
import nstv.animationshow.common.design.Grid
import nstv.animationshow.common.screen.base.Piece

@Composable
fun Bar(bar: Piece, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(bar.percentage)
            .height(Grid.Four)
            .background(bar.color)
    ) {
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterEnd)
                .padding(Grid.Half),
            text = "#${bar.id}",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun Pie(pieces: List<Piece>, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .padding(Grid.Four)
        .aspectRatio(1f)
        .drawBehind {
            var currentAngle = 0f
            pieces.forEach {
                val sweepAngle = 360f * it.percentage
                drawArc(
                    color = it.color,
                    startAngle = currentAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    style = Fill
                )
                currentAngle += sweepAngle
            }
        }
    )
}
