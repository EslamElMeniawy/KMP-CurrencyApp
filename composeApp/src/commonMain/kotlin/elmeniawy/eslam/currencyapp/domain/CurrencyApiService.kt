package elmeniawy.eslam.currencyapp.domain

import elmeniawy.eslam.currencyapp.domain.model.Currency
import elmeniawy.eslam.currencyapp.domain.model.RequestState

/**
 * CurrencyApiService
 *
 * Created by Eslam El-Meniawy on 03-Aug-2025 at 11:18 AM.
 */
fun interface CurrencyApiService {
    suspend fun getLatestExchangeRates(): RequestState<List<Currency>?>
}