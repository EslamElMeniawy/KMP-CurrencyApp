package elmeniawy.eslam.currencyapp.data.remote.api

import elmeniawy.eslam.currencyapp.BuildKonfig
import elmeniawy.eslam.currencyapp.domain.CurrencyApiService
import elmeniawy.eslam.currencyapp.domain.PreferencesRepository
import elmeniawy.eslam.currencyapp.domain.model.ApiResponse
import elmeniawy.eslam.currencyapp.domain.model.Currency
import elmeniawy.eslam.currencyapp.domain.model.CurrencyCode
import elmeniawy.eslam.currencyapp.domain.model.RequestState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * CurrencyApiServiceImp
 *
 * Created by Eslam El-Meniawy on 31-Jul-2025 at 3:40 PM.
 */
class CurrencyApiServiceImp(private val _preferences: PreferencesRepository) : CurrencyApiService {
    companion object {
        const val ENDPOINT = "https://api.currencyapi.com/v3/latest"
        val API_KEY = BuildKonfig.API_KEY
    }

    private val _httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
        install(DefaultRequest) {
            headers {
                append("apikey", API_KEY)
            }
        }
    }

    override suspend fun getLatestExchangeRates(): RequestState<List<Currency>?> = try {
        val response = _httpClient.get(ENDPOINT)

        if (response.status.value == 200) {
            val apiResponse = Json.decodeFromString<ApiResponse?>(response.body())

            val availableCurrencyCodes = apiResponse?.data?.keys?.filter {
                CurrencyCode.entries.map { code -> code.name }.toSet().contains(it)
            }

            val availableCurrencies = apiResponse?.data?.values?.filter { currency ->
                availableCurrencyCodes?.contains(currency.code) == true
            }

            // Persist a timestamp.
            val lastUpdated = apiResponse?.meta?.lastUpdatedAt
            lastUpdated?.let { _preferences.saveLastUpdated(it) }

            RequestState.Success(data = availableCurrencies)
        } else {
            RequestState.Error(message = "HTTP Error Code: ${response.status}")
        }
    } catch (e: Exception) {
        RequestState.Error(message = e.message)
    }
}