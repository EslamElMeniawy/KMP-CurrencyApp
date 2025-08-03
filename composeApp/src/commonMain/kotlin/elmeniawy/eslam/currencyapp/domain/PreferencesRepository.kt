package elmeniawy.eslam.currencyapp.domain

/**
 * PreferencesRepository
 *
 * Created by Eslam El-Meniawy on 03-Aug-2025 at 2:39 PM.
 */
interface PreferencesRepository {
    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFresh(currentTimestamp: Long): Boolean
}