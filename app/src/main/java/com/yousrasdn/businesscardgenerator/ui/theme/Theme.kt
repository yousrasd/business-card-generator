package com.yousrasdn.businesscardgenerator.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PetraRose,
    secondary = PetraSandstone,
    tertiary = PetraTerracotta,
    background = Color(0xFF0F1419),
    surface = Color(0xFF1A1F24),
    surfaceVariant = Color(0xFF2A2F35),
    onPrimary = Color.White,
    onSecondary = Color(0xFF0A0E12),
    onTertiary = Color.White,
    onBackground = Color(0xFFE8EAED),
    onSurface = Color(0xFFE8EAED),
    primaryContainer = PetraRoseDark,
    secondaryContainer = PetraSandstoneDark,
    tertiaryContainer = PetraTerracottaDark,
    outline = Color(0xFF3A3F45)
)

private val LightColorScheme = lightColorScheme(
    primary = PetraRose,
    secondary = PetraSandstone,
    tertiary = PetraTerracotta,
    background = Color(0xFFFAFBFC),
    surface = Color.White,
    surfaceVariant = Color(0xFFF1F5F9),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A),
    primaryContainer = PetraRoseLight,
    secondaryContainer = PetraSandstoneLight,
    tertiaryContainer = PetraTerracottaLight,
    outline = Color(0xFFE2E8F0),
    surfaceTint = PetraRose
)

@Composable
fun BusinessCardGeneratorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
