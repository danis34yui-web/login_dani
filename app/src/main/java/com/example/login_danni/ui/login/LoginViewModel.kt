package com.example.login_danni.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login_danni.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onEmailChange(value: String) =
        _state.update { it.copy(email = value.trim(), emailError = null) }

    fun onPasswordChange(value: String) =
        _state.update { it.copy(password = value, passwordError = null) }

    fun onTogglePasswordVisibility() =
        _state.update { it.copy(passwordVisible = !it.passwordVisible) }

    fun onRememberChange(value: Boolean) =
        _state.update { it.copy(rememberMe = value) }

    fun onSnackbarShown() =
        _state.update { it.copy(snackbarMessage = null) }

    fun onLogout() = _state.update { LoginUiState() }

    fun onFeatureSoon() = _state.update { it.copy(snackbarMessage = R.string.feature_soon) }

    fun onLoginClick() {
        val s = _state.value
        if (s.isLoading) return

        val emailError = when {
            s.email.isEmpty() -> R.string.error_email_empty
            !Patterns.EMAIL_ADDRESS.matcher(s.email).matches() -> R.string.error_email_invalid
            else -> null
        }
        val passwordError = when {
            s.password.isEmpty() -> R.string.error_password_empty
            s.password.length < 8 -> R.string.error_password_short
            else -> null
        }
        if (emailError != null || passwordError != null) {
            _state.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1500) // Simula la llamada a un servidor (prototipo)
            val ok = s.email.equals("demo@correo.com", true) && s.password == "Demo1234"
            _state.update {
                if (ok) it.copy(isLoading = false, loggedIn = true)
                else it.copy(isLoading = false, snackbarMessage = R.string.error_credentials)
            }
        }
    }
}
