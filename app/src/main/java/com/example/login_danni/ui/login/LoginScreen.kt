package com.example.login_danni.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.login_danni.R
import com.example.login_danni.ui.theme.LoginDaniTheme

/** Punto de entrada: conecta el ViewModel con la UI sin estado. */
@Composable
fun LoginRoute(viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state.loggedIn) {
        WelcomeScreen(onLogout = viewModel::onLogout)
    } else {
        LoginScreen(
            state = state,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onTogglePassword = viewModel::onTogglePasswordVisibility,
            onRememberChange = viewModel::onRememberChange,
            onLogin = viewModel::onLoginClick,
            onSnackbarShown = viewModel::onSnackbarShown,
            onFeatureSoon = viewModel::onFeatureSoon
        )
    }
}

@Composable
fun LoginScreen(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePassword: () -> Unit,
    onRememberChange: (Boolean) -> Unit,
    onLogin: () -> Unit,
    onSnackbarShown: () -> Unit,
    onFeatureSoon: () -> Unit
) {
    val snackbarHost = remember { SnackbarHostState() }
    val context = LocalContext.current
    val focus = LocalFocusManager.current

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let {
            snackbarHost.showSnackbar(context.getString(it))
            onSnackbarShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHost) },
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 440.dp) // Tablets / plegables: contenido centrado
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logotipo
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(72.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Filled.Lock, contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    stringResource(R.string.login_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    stringResource(R.string.login_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(32.dp))

                // Correo
                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.login_email)) },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                    singleLine = true,
                    isError = state.emailError != null,
                    supportingText = state.emailError?.let { { Text(stringResource(it)) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) })
                )
                Spacer(Modifier.height(8.dp))

                // Contraseña
                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.login_password)) },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = onTogglePassword) {
                            Icon(
                                if (state.passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = stringResource(
                                    if (state.passwordVisible) R.string.login_hide_password
                                    else R.string.login_show_password
                                )
                            )
                        }
                    },
                    singleLine = true,
                    isError = state.passwordError != null,
                    supportingText = state.passwordError?.let { { Text(stringResource(it)) } },
                    visualTransformation = if (state.passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focus.clearFocus(); onLogin() })
                )

                // Recordarme + Olvidé mi contraseña
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = state.rememberMe, onCheckedChange = onRememberChange)
                        Text(
                            stringResource(R.string.login_remember),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    TextButton(onClick = onFeatureSoon) {
                        Text(stringResource(R.string.login_forgot))
                    }
                }
                Spacer(Modifier.height(16.dp))

                // Botón principal (con estado de carga)
                Button(
                    onClick = onLogin,
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(stringResource(R.string.login_button))
                    }
                }
                Spacer(Modifier.height(24.dp))

                // Separador
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(Modifier.weight(1f))
                    Text(
                        stringResource(R.string.login_or),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalDivider(Modifier.weight(1f))
                }
                Spacer(Modifier.height(24.dp))

                OutlinedButton(
                    onClick = onFeatureSoon,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) { Text(stringResource(R.string.login_google)) }

                Spacer(Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        stringResource(R.string.login_no_account),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    TextButton(onClick = onFeatureSoon) { Text(stringResource(R.string.login_register)) }
                }
                Text(
                    stringResource(R.string.demo_hint),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(onLogout: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Filled.CheckCircle, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(72.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.welcome_title), style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            OutlinedButton(onClick = onLogout) { Text(stringResource(R.string.welcome_logout)) }
        }
    }
}

@Preview(showBackground = true, name = "Claro")
@Composable
private fun LoginPreviewLight() = LoginDaniTheme(darkTheme = false, dynamicColor = false) {
    LoginScreen(LoginUiState(), {}, {}, {}, {}, {}, {}, {})
}

@Preview(showBackground = true, name = "Oscuro")
@Composable
private fun LoginPreviewDark() = LoginDaniTheme(darkTheme = true, dynamicColor = false) {
    LoginScreen(
        LoginUiState(email = "dani@correo", emailError = R.string.error_email_invalid),
        {}, {}, {}, {}, {}, {}, {}
    )
}
