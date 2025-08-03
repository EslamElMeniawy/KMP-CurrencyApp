package elmeniawy.eslam.currencyapp.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import elmeniawy.eslam.currencyapp.data.remote.api.CurrencyApiServiceImp

/**
 * HomeScreen
 *
 * Created by Eslam El-Meniawy on 03-Aug-2025 at 11:46 AM.
 */
class HomeScreen : Screen {
    @Composable
    override fun Content() {
        LaunchedEffect(Unit) {
            CurrencyApiServiceImp().getLatestExchangeRates()
        }
    }
}