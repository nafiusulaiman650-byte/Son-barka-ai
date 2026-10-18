package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MovieCreation
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.model.NavigationDestination
import com.example.ui.theme.GoldPrimary

@Composable
fun SonBarkaBottomBar(
  currentDestination: NavigationDestination,
  onNavigate: (NavigationDestination) -> Unit
) {
  NavigationBar(
    windowInsets = WindowInsets.navigationBars,
    containerColor = MaterialTheme.colorScheme.surface,
    contentColor = MaterialTheme.colorScheme.onSurface
  ) {
    // 1. Home
    NavigationBarItem(
      selected = currentDestination == NavigationDestination.HOME,
      onClick = { onNavigate(NavigationDestination.HOME) },
      icon = {
        Icon(
          imageVector = if (currentDestination == NavigationDestination.HOME) Icons.Filled.Home else Icons.Outlined.Home,
          contentDescription = "Home"
        )
      },
      label = { Text("Home") },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = GoldPrimary.copy(alpha = 0.2f),
        selectedIconColor = GoldPrimary,
        selectedTextColor = GoldPrimary
      ),
      modifier = Modifier.testTag("nav_home")
    )

    // 2. AI Video
    NavigationBarItem(
      selected = currentDestination == NavigationDestination.VIDEO_GEN,
      onClick = { onNavigate(NavigationDestination.VIDEO_GEN) },
      icon = {
        Icon(
          imageVector = if (currentDestination == NavigationDestination.VIDEO_GEN) Icons.Filled.MovieCreation else Icons.Outlined.MovieCreation,
          contentDescription = "AI Video"
        )
      },
      label = { Text("Video") },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = GoldPrimary.copy(alpha = 0.2f),
        selectedIconColor = GoldPrimary,
        selectedTextColor = GoldPrimary
      ),
      modifier = Modifier.testTag("nav_video")
    )

    // 3. AI Voice (Hausa & Fulfulde)
    NavigationBarItem(
      selected = currentDestination == NavigationDestination.VOICE_STUDIO,
      onClick = { onNavigate(NavigationDestination.VOICE_STUDIO) },
      icon = {
        Icon(
          imageVector = if (currentDestination == NavigationDestination.VOICE_STUDIO) Icons.Filled.Mic else Icons.Outlined.Mic,
          contentDescription = "Voice"
        )
      },
      label = { Text("Voices") },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = GoldPrimary.copy(alpha = 0.2f),
        selectedIconColor = GoldPrimary,
        selectedTextColor = GoldPrimary
      ),
      modifier = Modifier.testTag("nav_voice")
    )

    // 4. AI Story & Scripts
    NavigationBarItem(
      selected = currentDestination == NavigationDestination.SCRIPT_STUDIO,
      onClick = { onNavigate(NavigationDestination.SCRIPT_STUDIO) },
      icon = {
        Icon(
          imageVector = if (currentDestination == NavigationDestination.SCRIPT_STUDIO) Icons.Filled.AutoStories else Icons.Outlined.AutoStories,
          contentDescription = "Stories"
        )
      },
      label = { Text("Scripts") },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = GoldPrimary.copy(alpha = 0.2f),
        selectedIconColor = GoldPrimary,
        selectedTextColor = GoldPrimary
      ),
      modifier = Modifier.testTag("nav_story")
    )

    // 5. Gemini Visuals
    NavigationBarItem(
      selected = currentDestination == NavigationDestination.GEMINI_VISUALS,
      onClick = { onNavigate(NavigationDestination.GEMINI_VISUALS) },
      icon = {
        Icon(
          imageVector = if (currentDestination == NavigationDestination.GEMINI_VISUALS) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
          contentDescription = "Gemini Visuals"
        )
      },
      label = { Text("Visuals") },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = GoldPrimary.copy(alpha = 0.2f),
        selectedIconColor = GoldPrimary,
        selectedTextColor = GoldPrimary
      ),
      modifier = Modifier.testTag("nav_gemini_visuals")
    )

    // 6. Library
    NavigationBarItem(
      selected = currentDestination == NavigationDestination.PROJECTS,
      onClick = { onNavigate(NavigationDestination.PROJECTS) },
      icon = {
        Icon(
          imageVector = if (currentDestination == NavigationDestination.PROJECTS) Icons.Filled.VideoLibrary else Icons.Outlined.VideoLibrary,
          contentDescription = "Library"
        )
      },
      label = { Text("Creations") },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = GoldPrimary.copy(alpha = 0.2f),
        selectedIconColor = GoldPrimary,
        selectedTextColor = GoldPrimary
      ),
      modifier = Modifier.testTag("nav_projects")
    )
  }
}
