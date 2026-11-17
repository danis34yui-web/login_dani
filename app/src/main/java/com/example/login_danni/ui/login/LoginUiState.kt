package com.example.login_danni.ui.login

import androidx.annotation.StringRes

/** Estado inmutable de la pantalla (patrón UDF: el estado baja, los eventos suben). */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val rememberMe: Boolean = false,
    @StringRes val emailError: Int? = null,
    @StringRes val passwordError: Int? = null,
    val isLoading: Boolean = false,
    val loggedIn: Boolean = false,
    @StringRes val snackbarMessage: Int? = null
)
