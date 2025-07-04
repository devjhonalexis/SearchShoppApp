package jhon.solis.dev.searchshoppapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ShopAppCustomColors(
    val backgroundColor: Color,
    val textColorInit: Color,
)

val LocalCustomColors = staticCompositionLocalOf {
    ShopAppCustomColors(
        backgroundColor = Color.White,
        textColorInit = Color.Black,
    )
}

object ShopAppTheme {
    val colors: ShopAppCustomColors
        @Composable
        get() = LocalCustomColors.current
}