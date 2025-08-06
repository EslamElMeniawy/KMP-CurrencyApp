package elmeniawy.eslam.currencyapp.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import currencyapp.composeapp.generated.resources.Res
import currencyapp.composeapp.generated.resources.exchange_illustration
import currencyapp.composeapp.generated.resources.refresh_ic
import currencyapp.composeapp.generated.resources.switch_ic
import elmeniawy.eslam.currencyapp.domain.model.Currency
import elmeniawy.eslam.currencyapp.domain.model.CurrencyCode
import elmeniawy.eslam.currencyapp.domain.model.CurrencyType
import elmeniawy.eslam.currencyapp.domain.model.DisplayResult
import elmeniawy.eslam.currencyapp.domain.model.RateStatus
import elmeniawy.eslam.currencyapp.domain.model.RequestState
import elmeniawy.eslam.currencyapp.util.displayCurrentDateTime
import org.jetbrains.compose.resources.painterResource

/**
 * HomeHeader
 *
 * Created by Eslam El-Meniawy on 05-Aug-2025 at 10:06 AM.
 */

@Composable
fun HomeHeader(
    status: RateStatus,
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    amount: Double,
    onAmountChange: (Double) -> Unit,
    onRatesRefresh: () -> Unit,
    onSwitchClick: () -> Unit,
    onCurrencyTypeSelect: (CurrencyType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(all = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        RatesStatus(status = status, onRatesRefresh = onRatesRefresh)
        Spacer(modifier = Modifier.height(24.dp))

        CurrencyInputs(
            source = source,
            target = target,
            onSwitchClick = onSwitchClick,
            onCurrencyTypeSelect = onCurrencyTypeSelect
        )

        Spacer(modifier = Modifier.height(24.dp))
        AmountInput(amount = amount, onAmountChange = onAmountChange)
    }
}

@Composable
fun RatesStatus(status: RateStatus, onRatesRefresh: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(Res.drawable.exchange_illustration),
                contentDescription = "Exchange Rate Illustration"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = displayCurrentDateTime(),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = status.title,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = when (status) {
                        RateStatus.Stale -> MaterialTheme.colorScheme.error
                        RateStatus.Fresh -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }

        if (status == RateStatus.Stale) {
            IconButton(onClick = onRatesRefresh) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.refresh_ic),
                    contentDescription = "Refresh Icon",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun CurrencyInputs(
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    onSwitchClick: () -> Unit,
    onCurrencyTypeSelect: (CurrencyType) -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }

    val animatedRotation by animateFloatAsState(
        targetValue = if (animationStarted) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyView(placeholder = "From", currency = source, onClick = {
            if (source.isSuccess()) {
                onCurrencyTypeSelect(
                    CurrencyType.Source(
                        currencyCode = CurrencyCode.valueOf(
                            source.getSuccessData()?.code ?: ""
                        )
                    )
                )
            }
        })
        Spacer(modifier = Modifier.height(14.dp))

        IconButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .graphicsLayer { rotationY = animatedRotation },
            onClick = {
                animationStarted = !animationStarted
                onSwitchClick()
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Switch Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        CurrencyView(placeholder = "To", currency = target, onClick = {
            if (target.isSuccess()) {
                onCurrencyTypeSelect(
                    CurrencyType.Target(
                        currencyCode = CurrencyCode.valueOf(
                            target.getSuccessData()?.code ?: ""
                        )
                    )
                )
            }
        })
    }
}

@Composable
fun RowScope.CurrencyView(
    placeholder: String,
    currency: RequestState<Currency>,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeholder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                .height(54.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            currency.DisplayResult(onSuccess = { data ->
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(
                        CurrencyCode.valueOf(
                            data?.code ?: ""
                        ).flag
                    ),
                    tint = Color.Unspecified,
                    contentDescription = "Country Flag"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = CurrencyCode.valueOf(data?.code ?: "").name,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                )
            })
        }
    }
}

@Composable
fun AmountInput(amount: Double, onAmountChange: (Double) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .animateContentSize()
            .height(54.dp),
        value = "$amount",
        onValueChange = { onAmountChange(it.toDoubleOrNull() ?: 0.0) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            errorContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}