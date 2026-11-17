package com.example.login_danni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.login_danni.ui.login.LoginRoute
import com.example.login_danni.ui.login.LoginViewModel
import com.example.login_danni.ui.theme.LoginDaniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // dynamicColor = true -> Material You (Android 12+). En versiones anteriores usa la paleta estática.
            LoginDaniTheme(dynamicColor = true) {
                val vm: LoginViewModel = viewModel()
                LoginRoute(viewModel = vm)
            }
        }
    }
}
