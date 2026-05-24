package pl.kacper.misterski.rangestats.feature.history.domain.repository

import pl.kacper.misterski.rangestats.core.domain.models.Session

interface HistoryRepository {
    suspend fun getSessions(): List<Session>
    suspend fun getSession(id: String): Session?
    suspend fun deleteSession(id: String)
}
