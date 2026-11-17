package com.example.login_danni.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Escala tipográfica Material 3 (se personalizan sólo los estilos usados en el login).
val AppTypography = Typography(
    headlineMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 28.sp, lineHeight = 36.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp)
)
