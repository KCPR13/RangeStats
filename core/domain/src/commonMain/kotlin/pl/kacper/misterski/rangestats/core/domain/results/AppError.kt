package pl.kacper.misterski.rangestats.core.domain.results

sealed class AppError(cause: Throwable, message: String? = null) : Exception(message, cause) {
    class NetworkError(cause: Throwable, message: String? = null) : AppError( cause,message)
    class DatabaseError(cause: Throwable, message: String? = null) : AppError(cause,message)
    class VisionError(cause: Throwable, message: String? = null) : AppError( cause,message)
    class UnknownError(cause: Throwable, message: String? = null) : AppError( cause,message)
}
