package pl.kacper.misterski.rangestats.feature.settings.ui

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class WeaponAddedNotifier {
    private val events = Channel<Unit>() // TODO why unit?
    val onWeaponAdded get()  = events.receiveAsFlow()

    suspend fun notify() {
        events.send(Unit)
    }
}
