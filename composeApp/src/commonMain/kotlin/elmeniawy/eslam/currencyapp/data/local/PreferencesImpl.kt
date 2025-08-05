@file:OptIn(ExperimentalSettingsApi::class, ExperimentalTime::class)

package elmeniawy.eslam.currencyapp.data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import elmeniawy.eslam.currencyapp.domain.PreferencesRepository
import elmeniawy.eslam.currencyapp.domain.model.Currency
import elmeniawy.eslam.currencyapp.domain.model.CurrencyCode
import elmeniawy.eslam.currencyapp.domain.model.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * PreferencesImpl
 *
 * Created by Eslam El-Meniawy on 03-Aug-2025 at 2:41 PM.
 */
class PreferencesImpl(private val _settings: Settings) : PreferencesRepository {
    companion object {
        const val TIMESTAMP_KEY = "lastUpdated"
        const val CURRENCIES_KEY = "lastUpdated"
        const val SOURCE_CURRENCY_KEY = "sourceCurrency"
        const val TARGET_CURRENCY_KEY = "targetCurrency"

        val DEFAULT_SOURCE_CURRENCY = CurrencyCode.USD.name
        val DEFAULT_TARGET_CURRENCY = CurrencyCode.EUR.name
    }

    private val _flowSettings: FlowSettings = (_settings as ObservableSettings).toFlowSettings()

    override suspend fun saveLastUpdated(lastUpdated: String) {
        _flowSettings.putLong(
            key = TIMESTAMP_KEY,
            value = Instant.parse(lastUpdated).toEpochMilliseconds()
        )
    }

    override suspend fun isDataFresh(currentTimestamp: Long): Boolean {
        val savedTimestamp = _flowSettings.getLong(key = TIMESTAMP_KEY, defaultValue = 0L)

        return if (savedTimestamp != 0L) {
            val currentInstant = Instant.fromEpochMilliseconds(currentTimestamp)
            val savedInstant = Instant.fromEpochMilliseconds(savedTimestamp)
            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val daysDifference = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear
            daysDifference < 1
        } else false
    }

    override suspend fun insertCurrencyData(currencies: List<Currency>) {
        _flowSettings.putString(key = CURRENCIES_KEY, value = Json.encodeToString(currencies))
    }

    override fun readCurrencyData(): Flow<RequestState<List<Currency>>> =
        _flowSettings.getStringOrNullFlow(key = CURRENCIES_KEY)
            .map { json ->
                when {
                    json.isNullOrBlank() -> RequestState.Error(message = "No Data Found.")
                    else -> RequestState.Success(data = Json.decodeFromString<List<Currency>>(json))
                }
            }

    override suspend fun cleanUpCurrency() {
        _flowSettings.remove(CURRENCIES_KEY)
    }

    override suspend fun saveSourceCurrencyCode(code: String) {
        _flowSettings.putString(key = SOURCE_CURRENCY_KEY, value = code)
    }

    override suspend fun saveTargetCurrencyCode(code: String) {
        _flowSettings.putString(key = TARGET_CURRENCY_KEY, value = code)
    }

    override fun readSourceCurrencyCode(): Flow<CurrencyCode> = _flowSettings.getStringFlow(
        key = SOURCE_CURRENCY_KEY,
        defaultValue = DEFAULT_SOURCE_CURRENCY
    ).map { CurrencyCode.valueOf(it) }


    override fun readTargetCurrencyCode(): Flow<CurrencyCode> = _flowSettings.getStringFlow(
        key = TARGET_CURRENCY_KEY,
        defaultValue = DEFAULT_TARGET_CURRENCY
    ).map { CurrencyCode.valueOf(it) }
}