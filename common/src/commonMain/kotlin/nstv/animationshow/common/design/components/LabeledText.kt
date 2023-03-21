package nstv.animationshow.common.design.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun LabeledText(label: String, body: String) {
    Text(
        buildAnnotatedString {
            withStyle(
                MaterialTheme.typography.titleMedium.toSpanStyle()
                    .copy(color = MaterialTheme.colorScheme.onBackground)
            ) {
                append(label)
            }
            withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
                append(body)
            }
        }
    )
}
