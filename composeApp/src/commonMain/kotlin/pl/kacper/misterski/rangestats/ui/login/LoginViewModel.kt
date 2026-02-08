package pl.kacper.misterski.rangestats.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class LoginViewModel : ViewModel() {

    private val _uiModel = MutableStateFlow(LoginUiModel())
    val uiModel = _uiModel.asStateFlow()

    init {
        println("$TAG, init")
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailChanged -> _uiModel.update { it.copy(email = action.email) }
            LoginAction.LoginClick -> Unit
            is LoginAction.PasswordChanged -> _uiModel.update { it.copy(password = action.password) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("$TAG, onCleared")

    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}