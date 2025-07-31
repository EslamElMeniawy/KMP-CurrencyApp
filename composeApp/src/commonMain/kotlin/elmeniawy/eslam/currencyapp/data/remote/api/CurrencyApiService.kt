package elmeniawy.eslam.currencyapp.data.remote.api

import elmeniawy.eslam.currencyapp.BuildKonfig

/**
 * CurrencyApiService
 *
 * Created by Eslam El-Meniawy on 31-Jul-2025 at 3:40 PM.
 */
class CurrencyApiService {
    companion object {
        const val ENDPOINT = "https://api.currencyapi.com/v3/latest"
        val API_KEY = BuildKonfig.API_KEY
    }
}