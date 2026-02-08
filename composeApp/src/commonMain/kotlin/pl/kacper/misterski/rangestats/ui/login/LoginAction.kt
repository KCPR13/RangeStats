package pl.kacper.misterski.rangestats.ui.login

sealed class LoginAction {
    data class EmailChanged(val email: String) : LoginAction()
    data class PasswordChanged(val password: String) : LoginAction()
    data object LoginClick : LoginAction()
}