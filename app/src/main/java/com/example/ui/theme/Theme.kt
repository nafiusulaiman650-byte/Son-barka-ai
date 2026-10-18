package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = GoldPrimary,
  onPrimary = Color.Black,
  primaryContainer = SurfaceHighlightDark,
  onPrimaryContainer = GoldLight,
  secondary = OchreSecondary,
  onSecondary = Color.White,
  secondaryContainer = OchreDark,
  onSecondaryContainer = OchreLight,
  tertiary = IndigoTertiary,
  onTertiary = Color.White,
  tertiaryContainer = IndigoDark,
  onTertiaryContainer = IndigoTertiary,
  background = BackgroundDark,
  onBackground = OnSurfaceLight,
  surface = SurfaceDark,
  onSurface = OnSurfaceLight,
  surfaceVariant = SurfaceVariantDark,
  onSurfaceVariant = OnSurfaceMuted,
  outline = BorderSubtle,
)

private val LightColorScheme = lightColorScheme(
  primary = GoldDark,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFFEF3C7),
  onPrimaryContainer = Color(0xFF78350F),
  secondary = OchreSecondary,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFFFEDD5),
  onSecondaryContainer = Color(0xFF7C2D12),
  tertiary = IndigoDark,
  onTertiary = Color.White,
  background = Color(0xFFFCFAF7),
  onBackground = Color(0xFF1E1A17),
  surface = Color(0xFFFFFFFF),
  onSurface = Color(0xFF1E1A17),
  surfaceVariant = Color(0xFFF3ECE4),
  onSurfaceVariant = Color(0xFF5B5249),
  outline = Color(0xFFD8CEBF),
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to stunning dark studio aesthetic
  dynamicColor: Boolean = false, // Keep consistent African Studio branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
