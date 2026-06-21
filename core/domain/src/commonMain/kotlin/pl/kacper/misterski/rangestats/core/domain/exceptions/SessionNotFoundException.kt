package pl.kacper.misterski.rangestats.core.domain.exceptions

class SessionNotFoundException(sessionId: String) :
    IllegalStateException("Session not found: $sessionId")