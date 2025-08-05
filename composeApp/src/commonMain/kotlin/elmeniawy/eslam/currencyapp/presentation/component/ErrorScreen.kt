package elmeniawy.eslam.currencyapp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/**
 * ErrorScreen
 *
 * Created by Eslam El-Meniawy on 05-Aug-2025 at 4:34 PM.
 */

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, message: String? = null) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message ?: "No data.", textAlign = TextAlign.Center)
    }
}