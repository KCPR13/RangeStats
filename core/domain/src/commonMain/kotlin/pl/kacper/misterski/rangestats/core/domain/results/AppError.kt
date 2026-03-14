package pl.kacper.misterski.rangestats.core.domain.results

// TODO nullable?
sealed class AppError(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class NetworkError(message: String, cause: Throwable? = null) : AppError(message, cause)
    class DatabaseError(message: String, cause: Throwable? = null) : AppError(message, cause)
    class VisionError(message: String, cause: Throwable? = null) : AppError(message, cause)
    class UnknownError(message: String, cause: Throwable? = null) : AppError(message, cause)
}
