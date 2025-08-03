package elmeniawy.eslam.currencyapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import elmeniawy.eslam.currencyapp.presentation.screen.HomeScreen
import elmeniawy.eslam.currencyapp.ui.theme.darkScheme
import elmeniawy.eslam.currencyapp.ui.theme.lightScheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val colors = if (isSystemInDarkTheme()) darkScheme else lightScheme

    MaterialTheme(colorScheme = colors) {
        Navigator(HomeScreen())
    }
}