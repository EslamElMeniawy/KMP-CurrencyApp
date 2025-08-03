package elmeniawy.eslam.currencyapp.domain.model

/**
 * RequestState
 *
 * Created by Eslam El-Meniawy on 03-Aug-2025 at 11:20 AM.
 */
sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<out T>(val data: T?) : RequestState<T>()
    data class Error(val message: String?) : RequestState<Nothing>()

    fun isLoading(): Boolean = this is Loading
    fun isError(): Boolean = this is Error
    fun isSuccess(): Boolean = this is Success
    fun getSuccessData(): T? = if (this is Success) this.data else null
    fun getErrorMessage(): String? = if (this is Error) this.message else null
}