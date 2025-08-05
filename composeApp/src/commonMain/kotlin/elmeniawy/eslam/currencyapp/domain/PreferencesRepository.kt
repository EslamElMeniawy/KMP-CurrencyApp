package elmeniawy.eslam.currencyapp.domain

import elmeniawy.eslam.currencyapp.domain.model.Currency
import elmeniawy.eslam.currencyapp.domain.model.CurrencyCode
import elmeniawy.eslam.currencyapp.domain.model.RequestState
import kotlinx.coroutines.flow.Flow

/**
 * PreferencesRepository
 *
 * Created by Eslam El-Meniawy on 03-Aug-2025 at 2:39 PM.
 */
interface PreferencesRepository {
    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFresh(currentTimestamp: Long): Boolean
    suspend fun insertCurrencyData(currencies: List<Currency>)
    fun readCurrencyData(): Flow<RequestState<List<Currency>>>
    suspend fun cleanUpCurrency()
    suspend fun saveSourceCurrencyCode(code: String)
    suspend fun saveTargetCurrencyCode(code: String)
    fun readSourceCurrencyCode(): Flow<CurrencyCode>
    fun readTargetCurrencyCode(): Flow<CurrencyCode>
}