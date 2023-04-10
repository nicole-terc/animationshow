package nstv.animationshow.common.design

import androidx.compose.ui.graphics.Color

val slidesBackground = Color(0xFF4285f4)

object TileColor {
    val Blue = Color(0xFF1976D2)
    val Green = Color(0xFF3DDC84)
    val Magenta = Color(0xFFC51162)
    val Yellow = Color(0xFFFFEB3B)
    val Purple = Color(0xFF6200EA)
    val Orange = Color(0xFFFF9800)
    val Red = Color(0xFFFF0000)
    val DarkGreen = Color(0xFF24804D)
    val LightGray = Color(0xFFCCCCCC)
    val DarkGray = Color(0xFF444444)
    val BlueSlides = Color(0xFF4285f4)

    val list = listOf(
        Blue,
        Green,
        Magenta,
        Yellow,
        Purple,
        Orange,
        Red,
        DarkGreen,
        LightGray,
        DarkGray,
    )

    val map = mapOf<String, Color>(
        "Blue" to Blue,
        "Green" to Green,
        "Magenta" to Magenta,
        "Yellow" to Yellow,
        "Purple" to Purple,
        "Orange" to Orange,
        "Red" to Red,
        "DarkGreen" to DarkGreen,
        "LightGray" to LightGray,
        "DarkGray" to DarkGray,
    )

    fun random() = list.random()
    fun randomFilter(filter: Color) = list.filter { it != filter }.random()
}

val md_theme_light_primary = Color(0xFF005CBB)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFD7E3FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001B3F)
val md_theme_light_secondary = Color(0xFF565E71)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFDAE2F9)
val md_theme_light_onSecondaryContainer = Color(0xFF131C2B)
val md_theme_light_tertiary = Color(0xFF705574)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFAD8FD)
val md_theme_light_onTertiaryContainer = Color(0xFF29132E)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFDFBFF)
val md_theme_light_onBackground = Color(0xFF1A1B1F)
val md_theme_light_surface = Color(0xFFFDFBFF)
val md_theme_light_onSurface = Color(0xFF1A1B1F)
val md_theme_light_surfaceVariant = Color(0xFFE0E2EC)
val md_theme_light_onSurfaceVariant = Color(0xFF44474E)
val md_theme_light_outline = Color(0xFF74777F)
val md_theme_light_inverseOnSurface = Color(0xFFF2F0F4)
val md_theme_light_inverseSurface = Color(0xFF2F3033)
val md_theme_light_inversePrimary = Color(0xFFABC7FF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF005CBB)
val md_theme_light_outlineVariant = Color(0xFFC4C6D0)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFABC7FF)
val md_theme_dark_onPrimary = Color(0xFF002F65)
val md_theme_dark_primaryContainer = Color(0xFF00458F)
val md_theme_dark_onPrimaryContainer = Color(0xFFD7E3FF)
val md_theme_dark_secondary = Color(0xFFBEC6DC)
val md_theme_dark_onSecondary = Color(0xFF283041)
val md_theme_dark_secondaryContainer = Color(0xFF3E4759)
val md_theme_dark_onSecondaryContainer = Color(0xFFDAE2F9)
val md_theme_dark_tertiary = Color(0xFFDDBCE0)
val md_theme_dark_onTertiary = Color(0xFF3F2844)
val md_theme_dark_tertiaryContainer = Color(0xFF573E5C)
val md_theme_dark_onTertiaryContainer = Color(0xFFFAD8FD)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1A1B1F)
val md_theme_dark_onBackground = Color(0xFFE3E2E6)
val md_theme_dark_surface = Color(0xFF1A1B1F)
val md_theme_dark_onSurface = Color(0xFFE3E2E6)
val md_theme_dark_surfaceVariant = Color(0xFF44474E)
val md_theme_dark_onSurfaceVariant = Color(0xFFC4C6D0)
val md_theme_dark_outline = Color(0xFF8E9099)
val md_theme_dark_inverseOnSurface = Color(0xFF1A1B1F)
val md_theme_dark_inverseSurface = Color(0xFFE3E2E6)
val md_theme_dark_inversePrimary = Color(0xFF005CBB)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFABC7FF)
val md_theme_dark_outlineVariant = Color(0xFF44474E)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFF0072E4)
