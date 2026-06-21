package com.example.ui.theme

import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = Color(0xFF81C784),      // Vibrant nature green
    secondary = Color(0xFFA5D6A7),    // Light leaf green
    tertiary = Color(0xFFFFD54F),     // Sunny gold yellow
    background = Color(0xFF0C1D0E),   // Velvet Forest dark backdrop
    surface = Color(0xFF142E17),      // Layered forest foliage green
    onPrimary = Color(0xFF003308),
    onSecondary = Color(0xFF003810),
    onTertiary = Color(0xFF3E2723),
    onBackground = Color(0xFFE8F5E9),
    onSurface = Color(0xFFE8F5E9)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = Color(0xFF2E7D32),      // Deep forest green
    secondary = Color(0xFF4CAF50),    // Lively leaf green
    tertiary = Color(0xFFFBC02D),     // Sunshine golden yellow
    background = Color(0xFFF1F8E9),   // Soft mint paper
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color(0xFF3E2723),
    onBackground = Color(0xFF1B5E20),
    onSurface = Color(0xFF1B5E20)
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
