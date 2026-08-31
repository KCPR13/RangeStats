package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.ShotgunGauge
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Ammunition
import pl.kacper.misterski.rangestats.core.domain.usecase.AddWeaponUseCase
import pl.kacper.misterski.rangestats.core.navigation.Navigator
import pl.kacper.misterski.rangestats.feature.settings.ui.WeaponAddedNotifier

class AddWeaponViewModel(
    private val addWeaponUseCase: AddWeaponUseCase,
    private val navigator: Navigator,
    private val weaponAddedNotifier: WeaponAddedNotifier,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(AddWeaponUiModel())
    val uiModel: StateFlow<AddWeaponUiModel> = _uiModel.asStateFlow()

    fun onAction(action: AddWeaponAction) {
        when (action) {
            AddWeaponAction.OnStart -> loadWeaponTypeOptions(_uiModel.value.selectedType)
            is AddWeaponAction.NameChanged -> _uiModel.update { it.copy(name = action.name) }
            is AddWeaponAction.TypeSelected -> onTypeSelected(action.type)
            is AddWeaponAction.AmmoSelected -> onAmmoSelected(action.option)
            AddWeaponAction.Save -> save()
            AddWeaponAction.Dismiss -> navigator.back()
        }
    }

    private fun loadWeaponTypeOptions(selected: WeaponType) {
        viewModelScope.launch {
            val options = weaponTypeOptionsUiModel(selected)
            _uiModel.update { it.copy(weaponTypeOptions = options) }
        }
    }

    private fun onTypeSelected(type: WeaponType) {
        _uiModel.update {
            it.copy(
                selectedType = type,
                weaponTypeOptions = it.weaponTypeOptions.map { option -> option.copy(selected = option.type == type) },
                ammoOptions = type.toAmmoOptionUiModels(),
                ammoLabelRes = type.toAmmoLabelRes(),
            )
        }
    }

    private fun onAmmoSelected(option: AddWeaponUiModel.AmmoOptionUiModel) {
        _uiModel.update { state ->
            state.copy(ammoOptions = state.ammoOptions.map { it.copy(selected = it.key == option.key) })
        }
    }

    private fun save() {
        val state = _uiModel.value
        if (state.name.isBlank()) return
        val selectedKey = state.ammoOptions.find { it.selected }?.key ?: return
        val ammunition = if (state.selectedType == WeaponType.SHOTGUN) {
            Ammunition.GaugeAmmo(ShotgunGauge.valueOf(selectedKey))
        } else {
            Ammunition.CaliberAmmo(Caliber.valueOf(selectedKey))
        }

        viewModelScope.launch {
            _uiModel.update { it.copy(isSaving = true) }
            addWeaponUseCase(
                name = state.name,
                weaponType = state.selectedType,
                ammunition = ammunition
            )
                .catch { _uiModel.update { it.copy(isSaving = false) } }
                .collect {
                    _uiModel.update { it.copy(isSaving = false) }
                    weaponAddedNotifier.notify()
                    navigator.back()
                }
        }
    }
}