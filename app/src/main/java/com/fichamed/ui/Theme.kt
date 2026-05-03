package com.fichamed.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Primary       = Color(0xFF1565C0)
val PrimaryDark   = Color(0xFF0D47A1)
val PrimaryLight  = Color(0xFFE3F2FD)
val Accent        = Color(0xFF00ACC1)
val AccentLight   = Color(0xFFE0F7FA)
val Success       = Color(0xFF2E7D32)
val SuccessLight  = Color(0xFFE8F5E9)
val ErrorColor    = Color(0xFFC62828)
val ErrorLight    = Color(0xFFFFEBEE)
val Surface       = Color(0xFFF8FAFB)
val CardBg        = Color(0xFFFFFFFF)
val TextPrimary   = Color(0xFF1A2332)
val TextSecondary = Color(0xFF64748B)
val Border        = Color(0xFFE2E8F0)

private val LightColorScheme = lightColorScheme(
    primary         = Primary,
    onPrimary       = Color.White,
    primaryContainer= PrimaryLight,
    secondary       = Accent,
    background      = Surface,
    surface         = CardBg,
    onBackground    = TextPrimary,
    onSurface       = TextPrimary,
    error           = ErrorColor,
)

@Composable
fun FichaMedTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = Typography(),
        content     = content
    )
}
