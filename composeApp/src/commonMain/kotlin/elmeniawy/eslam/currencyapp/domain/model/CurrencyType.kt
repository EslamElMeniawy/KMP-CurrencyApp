package elmeniawy.eslam.currencyapp.domain.model

/**
 * CurrencyType
 *
 * Created by Eslam El-Meniawy on 05-Aug-2025 at 4:00 PM.
 */
sealed class CurrencyType(val code: CurrencyCode) {
    data class Source(val currencyCode: CurrencyCode) : CurrencyType(currencyCode)
    data class Target(val currencyCode: CurrencyCode) : CurrencyType(currencyCode)
    data object None : CurrencyType(CurrencyCode.USD)
}