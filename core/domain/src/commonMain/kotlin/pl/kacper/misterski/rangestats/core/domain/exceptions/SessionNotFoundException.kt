package pl.kacper.misterski.rangestats.core.domain.exceptions

class SessionNotFoundException(
    sessionId: Long,
) : IllegalStateException("Session not found: $sessionId")
