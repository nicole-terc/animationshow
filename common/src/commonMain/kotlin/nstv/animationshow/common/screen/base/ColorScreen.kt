package nstv.animationshow.common.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import nstv.animationshow.common.design.TileColor

@Composable
fun ColorScreen(
    modifier: Modifier = Modifier,
    color: Color = TileColor.random(),
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize().background(color = color),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
