package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavigationDestination
import com.example.model.UserTier
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.OchreSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SonBarkaTopBar(
  currentDestination: NavigationDestination,
  creditBalance: Int,
  userTier: UserTier,
  onNavigate: (NavigationDestination) -> Unit
) {
  Surface(
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 4.dp,
    modifier = Modifier.fillMaxWidth()
  ) {
    TopAppBar(
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(GoldPrimary)
              .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "SB",
              color = Color.Black,
              fontWeight = FontWeight.Black,
              fontSize = 16.sp
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "SON BARKA",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "AI",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                color = GoldPrimary
              )
            }
            Text(
              text = "AFRICAN CINEMATIC STUDIO",
              style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      },
      actions = {
        // Credits Pill
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, GoldPrimary.copy(alpha = 0.4f), CircleShape)
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .testTag("credits_pill")
        ) {
          Icon(
            imageVector = Icons.Default.Bolt,
            contentDescription = "Credits",
            tint = GoldPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "$creditBalance",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Tier Badge
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(CircleShape)
            .background(OchreSecondary.copy(alpha = 0.15f))
            .border(1.dp, OchreSecondary.copy(alpha = 0.5f), CircleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Diamond,
            contentDescription = "Tier",
            tint = OchreSecondary,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = userTier.title.substringBefore(" "),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = OchreSecondary
          )
        }

        // Library Shortcut
        IconButton(
          onClick = { onNavigate(NavigationDestination.PROJECTS) },
          modifier = Modifier.testTag("top_library_button")
        ) {
          Icon(
            imageVector = Icons.Default.VideoLibrary,
            contentDescription = "Creations Library",
            tint = if (currentDestination == NavigationDestination.PROJECTS) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        // Settings / Architecture Shortcut
        IconButton(
          onClick = { onNavigate(NavigationDestination.SETTINGS) },
          modifier = Modifier.testTag("top_settings_button")
        ) {
          Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "AI Architecture & Settings",
            tint = if (currentDestination == NavigationDestination.SETTINGS) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface
      )
    )
  }
}
