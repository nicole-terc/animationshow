package nstv.animationshow.common.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nstv.animationshow.common.design.AnimationsShowTheme
import nstv.animationshow.common.screen.MainContent

@Composable
fun App() {
    AnimationsShowTheme {
        MainContent(modifier = Modifier.fillMaxSize())
    }
}
