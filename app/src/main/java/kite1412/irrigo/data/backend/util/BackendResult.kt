package kite1412.irrigo.data.backend.util

sealed interface BackendResult<out T> {
    data class Success<T>(
        val data: T? = null,
        val message: String? = null
    ) : BackendResult<T>
    data class Error(
        val message: String,
        val throwable: Throwable
    ) : BackendResult<Nothing>
}