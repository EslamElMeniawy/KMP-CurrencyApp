package elmeniawy.eslam.currencyapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * ApiResponse
 *
 * Created by Eslam El-Meniawy on 03-Aug-2025 at 11:12 AM.
 */

@Serializable
data class ApiResponse(
    val meta: MetaData?,
    val data: Map<String, Currency>?
)

@Serializable
data class MetaData(
    @SerialName("last_updated_at")
    val lastUpdatedAt: String?
)

@Serializable
data class Currency(
    val code: String?,
    val value: Double?
)