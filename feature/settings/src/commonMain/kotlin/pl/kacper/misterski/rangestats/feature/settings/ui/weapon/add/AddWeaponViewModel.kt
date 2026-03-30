package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.AddWeaponUseCase

class AddWeaponViewModel(
    private val addWeapon: AddWeaponUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(AddWeaponUiModel())
    val uiModel: StateFlow<AddWeaponUiModel> = _uiModel.asStateFlow()

    fun onAction(action: AddWeaponAction) {
        when (action) {
            is AddWeaponAction.NameChanged -> _uiModel.update { it.copy(name = action.name) }
            is AddWeaponAction.TypeSelected -> _uiModel.update { it.copy(selectedType = action.type) }
            is AddWeaponAction.CaliberSelected -> onCaliberSelected(action.caliber)
            AddWeaponAction.Save -> save()
            AddWeaponAction.Reset -> _uiModel.update { AddWeaponUiModel() }
        }
    }

    private fun onCaliberSelected(caliber: AddWeaponUiModel.CaliberUiModel ) {
        val calibers =_uiModel.value.calibers.toMutableList()
        val newCalibers = calibers.map { it.copy(selected = it.name == caliber.name) }
        _uiModel.update { it.copy(calibers = newCalibers) }
    }

    private fun save() {
        val state = _uiModel.value
        if (state.name.isBlank()) return
        val selectedCaliber = _uiModel.value.calibers.find { it.selected } ?: return

        viewModelScope.launch {
            _uiModel.update { it.copy(isSaving = true) }
            addWeapon(name = state.name, type = state.selectedType, caliber = selectedCaliber.name)
                .onSuccess { _uiModel.update { it.copy(isSaving = false, isSaved = true) } }
                .onFailure { _uiModel.update { it.copy(isSaving = false) } }
        }
    }
}
