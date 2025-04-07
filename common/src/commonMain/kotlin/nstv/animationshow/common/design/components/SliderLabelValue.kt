package nstv.animationshow.common.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import nstv.animationshow.common.design.Grid
import kotlin.math.round

@Composable
fun SliderLabelValue(
    text: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    showDecimal: Boolean = false,
    onValueChangeFinished: () -> Unit = {}
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
        Slider(
            modifier = Modifier
                .weight(5f)
                .padding(horizontal = Grid.One),
            value = value,
            valueRange = valueRange,
            onValueChange = { onValueChange(round(it)) },
            onValueChangeFinished = onValueChangeFinished
        )
        Text(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.small
                )
                .padding(Grid.Half)
                .weight(1f),
            text = if (showDecimal) value.toString() else value.toInt().toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

