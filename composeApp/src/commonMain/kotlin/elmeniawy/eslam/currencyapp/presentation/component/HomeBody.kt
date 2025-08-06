package elmeniawy.eslam.currencyapp.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import elmeniawy.eslam.currencyapp.domain.model.Currency
import elmeniawy.eslam.currencyapp.domain.model.RequestState
import elmeniawy.eslam.currencyapp.util.DoubleConverter
import elmeniawy.eslam.currencyapp.util.GetBebasFontFamily
import elmeniawy.eslam.currencyapp.util.calculateExchangeRate
import elmeniawy.eslam.currencyapp.util.convert

/**
 * HomeBody
 *
 * Created by Eslam El-Meniawy on 06-Aug-2025 at 11:16 AM.
 */

@Composable
fun HomeBody(
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    amount: Double
) {
    var exchangedAmount by rememberSaveable { mutableStateOf(0.0) }

    val animatedExchangedAmount by animateValueAsState(
        targetValue = exchangedAmount,
        animationSpec = tween(durationMillis = 300),
        typeConverter = DoubleConverter(),
    )

    Column(modifier = Modifier.fillMaxSize().imePadding()) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${(animatedExchangedAmount * 100).toLong() / 100.0}",
                fontSize = 60.sp,
                fontFamily = GetBebasFontFamily(),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            AnimatedVisibility(visible = source.isSuccess() && target.isSuccess()) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "1 ${source.getSuccessData()?.code ?: ""} = " +
                                "${target.getSuccessData()?.value ?: 0.0} " +
                                (target.getSuccessData()?.code ?: ""),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "1 ${target.getSuccessData()?.code ?: ""} = " +
                                "${source.getSuccessData()?.value ?: 0.0} " +
                                (source.getSuccessData()?.code ?: ""),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth().height(54.dp).padding(horizontal = 24.dp)
                .background(color = Color.Unspecified, shape = RoundedCornerShape(99.dp)),
            onClick = {
                if (source.isSuccess() && target.isSuccess()) {
                    val exchangeRate = calculateExchangeRate(
                        source = source.getSuccessData()?.value ?: 0.0,
                        target = target.getSuccessData()?.value ?: 0.0,
                    )

                    exchangedAmount = convert(amount = amount, exchangeRate = exchangeRate)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface
            )
        ) {
            Text(text = "Convert")
        }
    }
}