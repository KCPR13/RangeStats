package pl.kacper.misterski.rangestats.feature.settings.ui.weapon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.DeleteWeaponUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetWeaponsUseCase

class WeaponListViewModel(
    private val getWeapons: GetWeaponsUseCase,
    private val deleteWeapon: DeleteWeaponUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(WeaponListUiModel())
    val uiModel: StateFlow<WeaponListUiModel> = _uiModel.asStateFlow()

    fun onAction(action: WeaponListAction) {
        when (action) {
            is WeaponListAction.DeleteWeapon -> delete(action.id)
            WeaponListAction.OnStart -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true) }
            val weapons = getWeapons().getOrElse { emptyList() }
            _uiModel.update { it.copy(isLoading = false, weapons = weapons) }
        }
    }

    private fun delete(id: String) {
        viewModelScope.launch {
            deleteWeapon(id)
            fetchData()
        }
    }
}
