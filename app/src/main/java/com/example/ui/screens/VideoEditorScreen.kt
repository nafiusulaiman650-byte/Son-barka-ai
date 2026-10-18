package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.VideoProject
import com.example.model.VideoScene
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary

@Composable
fun VideoEditorScreen(
  activeProject: VideoProject?,
  onExport: () -> Unit
) {
  var selectedSceneIndex by remember { mutableStateOf(0) }
  var enableBilingualSubtitles by remember { mutableStateOf(true) }
  var backgroundMusicVolume by remember { mutableStateOf(0.6f) }
  var voiceoverVolume by remember { mutableStateOf(0.9f) }
  var selectedTransition by remember { mutableStateOf("Cinematic Cross-Dissolve") }

  val transitions = listOf("Cinematic Cross-Dissolve", "Sahelian Fade to Ochre", "Dynamic Whip Pan", "Direct Cut")
  val audioTracks = listOf(
    "Sahelian Kora & Kalangu Resonance",
    "Desert Winds & Fulani Flute",
    "Royal Kano Kakaki Fanfare",
    "Modern Afro-Cinematic Beat"
  )
  var selectedAudioTrack by remember { mutableStateOf(audioTracks[0]) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("video_editor_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header
    item {
      Column {
        Text(
          text = "AI Video Timeline Editor",
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Arrange connected clips, sync Hausa/Fulfulde audio tracks, add bilingual subtitles and transitions.",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    if (activeProject == null) {
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text("No active video project loaded. Generate a video from the Video tab first.")
          }
        }
      }
    } else {
      // 2. Main Player Canvas with Subtitle Overlay
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = Color.Black),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(220.dp)
          ) {
            Image(
              painter = painterResource(id = R.drawable.img_hero_banner),
              contentDescription = "Player preview",
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.Crop
            )

            // Play icon
            Box(
              modifier = Modifier
                .align(Alignment.Center)
                .size(54.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.6f))
                .border(2.dp, GoldPrimary, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = GoldPrimary,
                modifier = Modifier.size(32.dp)
              )
            }

            // Bilingual Subtitle Overlay
            val currentScene = activeProject.scenes.getOrNull(selectedSceneIndex) ?: activeProject.scenes.first()
            if (enableBilingualSubtitles) {
              Column(
                modifier = Modifier
                  .align(Alignment.BottomCenter)
                  .padding(12.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(Color.Black.copy(alpha = 0.85f))
                  .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text(
                  text = currentScene.narrationText,
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                  color = GoldPrimary,
                  fontSize = 13.sp
                )
                Text(
                  text = currentScene.englishSubtitle,
                  style = MaterialTheme.typography.bodySmall,
                  color = Color.White.copy(alpha = 0.8f),
                  fontSize = 11.sp
                )
              }
            }
          }
        }
      }

      // 3. Multi-Scene Timeline Sequencer
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Scene Timeline Clips",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Total: ${activeProject.totalDurationSec}s",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = GoldPrimary
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Timeline Blocks
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              items(activeProject.scenes.indices.toList()) { index ->
                val scene = activeProject.scenes[index]
                val isSelected = selectedSceneIndex == index
                Box(
                  modifier = Modifier
                    .width(130.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) GoldPrimary.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface)
                    .border(
                      2.dp,
                      if (isSelected) GoldPrimary else MaterialTheme.colorScheme.outline,
                      RoundedCornerShape(8.dp)
                    )
                    .clickable { selectedSceneIndex = index }
                    .padding(8.dp)
                ) {
                  Column {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                      Text(
                        text = "Scene ${scene.sceneNumber}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface
                      )
                      Text(
                        text = "${scene.durationSec}s",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                      text = scene.title,
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurface,
                      maxLines = 2
                    )
                  }
                }
              }
            }
          }
        }
      }

      // 4. Subtitle & Audio Track Controls
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            // Bilingual Subtitles Toggle
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ClosedCaption, contentDescription = null, tint = GoldPrimary)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = "Bilingual Subtitle Engine",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = "Simultaneous Hausa/Fulfulde + English translation",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }

              Switch(
                checked = enableBilingualSubtitles,
                onCheckedChange = { enableBilingualSubtitles = it },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = GoldPrimary,
                  checkedTrackColor = GoldPrimary.copy(alpha = 0.3f)
                )
              )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Audio Soundtrack Picker
            Text(
              text = "African Cultural Soundtrack:",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              items(audioTracks) { track ->
                val isSelected = selectedAudioTrack == track
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) OchreSecondary else MaterialTheme.colorScheme.surface)
                    .border(1.dp, if (isSelected) OchreSecondary else MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                  .clickable { selectedAudioTrack = track }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.MusicNote,
                      contentDescription = null,
                      tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                      modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = track,
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                      color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Volume Mixers
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("Voiceover Volume", style = MaterialTheme.typography.bodyMedium)
              Text("${(voiceoverVolume * 100).toInt()}%", color = GoldPrimary, fontWeight = FontWeight.Bold)
            }
            Slider(
              value = voiceoverVolume,
              onValueChange = { voiceoverVolume = it },
              colors = SliderDefaults.colors(thumbColor = GoldPrimary, activeTrackColor = GoldPrimary)
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("Soundtrack Volume", style = MaterialTheme.typography.bodyMedium)
              Text("${(backgroundMusicVolume * 100).toInt()}%", color = OchreSecondary, fontWeight = FontWeight.Bold)
            }
            Slider(
              value = backgroundMusicVolume,
              onValueChange = { backgroundMusicVolume = it },
              colors = SliderDefaults.colors(thumbColor = OchreSecondary, activeTrackColor = OchreSecondary)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Transition picker
            Text(
              text = "Scene Transition Style:",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              items(transitions) { trans ->
                val isSelected = selectedTransition == trans
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) IndigoTertiary else MaterialTheme.colorScheme.surface)
                    .border(1.dp, if (isSelected) IndigoTertiary else MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                    .clickable { selectedTransition = trans }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                  Text(
                    text = trans,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
              onClick = onExport,
              colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
            ) {
              Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = Color.Black)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Render & Export Final Video (${activeProject.resolution.label})", color = Color.Black, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}
