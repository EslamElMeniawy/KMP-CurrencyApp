package elmeniawy.eslam.currencyapp.domain.model

/**
 * RateStatus
 *
 * Created by Eslam El-Meniawy on 05-Aug-2025 at 9:55 AM.
 */
enum class RateStatus(val title: String) {
    Idle(title = "Rate"),
    Fresh(title = "Fresh rates"),
    Stale(title = "Rates are not fresh")
}