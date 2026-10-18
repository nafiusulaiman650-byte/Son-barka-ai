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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.model.AspectRatio
import com.example.model.Language
import com.example.model.NavigationDestination
import com.example.model.VideoProject
import com.example.model.VideoResolution
import com.example.model.VideoScene
import com.example.model.VideoStyle
import com.example.service.ModularAiCoordinator
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary

@Composable
fun VideoGeneratorScreen(
  videoPrompt: String,
  selectedStyle: VideoStyle,
  selectedAspectRatio: AspectRatio,
  selectedResolution: VideoResolution,
  selectedLanguage: Language,
  videoDurationSec: Int,
  isVideoGenerating: Boolean,
  generationProgress: Float,
  activeProject: VideoProject?,
  onPromptChange: (String) -> Unit,
  onStyleChange: (VideoStyle) -> Unit,
  onAspectRatioChange: (AspectRatio) -> Unit,
  onResolutionChange: (VideoResolution) -> Unit,
  onLanguageChange: (Language) -> Unit,
  onDurationChange: (Int) -> Unit,
  onGenerate: () -> Unit,
  onNavigateToEditor: () -> Unit
) {
  var isTextToVideoTab by remember { mutableStateOf(true) }
  var expandedSceneNumber by remember { mutableStateOf<Int?>(1) }

  val creditCost = ModularAiCoordinator.calculateVideoCreditCost(videoDurationSec, selectedResolution)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("video_generator_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header & Mode Switcher
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "AI Video Generator",
              style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Multi-scene African cinematic generation (30s, 60s, 120s+)",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(GoldPrimary.copy(alpha = 0.15f))
              .border(1.dp, GoldPrimary.copy(alpha = 0.5f), CircleShape)
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Cost: $creditCost pts",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = GoldPrimary
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Mode Tabs
        TabRow(
          selectedTabIndex = if (isTextToVideoTab) 0 else 1,
          containerColor = MaterialTheme.colorScheme.surfaceVariant,
          indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
              Modifier.tabIndicatorOffset(tabPositions[if (isTextToVideoTab) 0 else 1]),
              color = GoldPrimary
            )
          }
        ) {
          Tab(
            selected = isTextToVideoTab,
            onClick = { isTextToVideoTab = true },
            text = { Text("Text-to-Video", fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = !isTextToVideoTab,
            onClick = { isTextToVideoTab = false },
            text = { Text("Image-to-Video", fontWeight = FontWeight.Bold) }
          )
        }
      }
    }

    // 2. Prompt Input & Cultural Suggestions
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          if (!isTextToVideoTab) {
            // Image-to-Video reference selector
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.3f))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)),
              contentAlignment = Alignment.Center
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.CameraAlt,
                  contentDescription = null,
                  tint = GoldPrimary,
                  modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = "Reference Image Loaded",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = "Character face & costume locked for multi-scene continuity",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }
            Spacer(modifier = Modifier.height(12.dp))
          }

          Text(
            text = if (isTextToVideoTab) "Cinematic Story Prompt" else "Motion & Narrative Direction",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(6.dp))

          OutlinedTextField(
            value = videoPrompt,
            onValueChange = onPromptChange,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("video_prompt_input"),
            minLines = 3,
            maxLines = 5,
            placeholder = {
              Text("Describe your African film, characters, action, costumes, setting, and mood...")
            },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GoldPrimary,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Preset Suggestions
          Text(
            text = "Cultural Presets:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
              PresetPromptChip("Hausa Royal Drama") {
                onPromptChange("Cinematic Hausa royal procession entering Kano city gates at golden sunset, royal emirs, traditional trumpet Kakaki and horses")
                onStyleChange(VideoStyle.HAUSA_NOLLYWOOD)
              }
            }
            item {
              PresetPromptChip("Fulani Pastoral Epic") {
                onPromptChange("A Fulani youth herding cattle across lush Adamawa plains at dawn, playing traditional wooden flute, morning mist")
                onStyleChange(VideoStyle.PASTORAL_NOMADIC)
              }
            }
            item {
              PresetPromptChip("Queen Amina Cavalry") {
                onPromptChange("Queen Amina of Zazzau astride a majestic warhorse inspecting ancient mud fortification walls with warriors, epic cinema")
                onStyleChange(VideoStyle.HISTORICAL_EPIC)
              }
            }
            item {
              PresetPromptChip("Sahel Afro-Futurism") {
                onPromptChange("Futuristic Northern Nigeria metropolis with solar-paneled mud minarets and flying transport vessels, indigo neon aesthetics")
                onStyleChange(VideoStyle.AFRO_FUTURISM)
              }
            }
          }
        }
      }
    }

    // 3. Duration & Multi-Scene Continuity (Critical Requirement)
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
            Column {
              Text(
                text = "Target Video Duration",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Multi-scene stitching preserves characters & lighting across scenes",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(EmeraldHausa.copy(alpha = 0.2f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "${(videoDurationSec / 15)} Connected Scenes",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = EmeraldHausa
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Duration Buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            listOf(30, 60, 90, 120).forEach { sec ->
              val isSelected = videoDurationSec == sec
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(10.dp))
                  .background(if (isSelected) GoldPrimary else MaterialTheme.colorScheme.surface)
                  .border(
                    1.dp,
                    if (isSelected) GoldPrimary else MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(10.dp)
                  )
                  .clickable { onDurationChange(sec) }
                  .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                    text = if (sec < 60) "${sec}s" else "${sec / 60}m",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = if (sec >= 60) "Long Film" else "Short",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) Color.Black.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }
          }
        }
      }
    }

    // 4. Aspect Ratio & Resolution & Language
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Aspect Ratio & Resolution",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Aspect Ratio row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            AspectRatio.values().forEach { ratio ->
              val selected = selectedAspectRatio == ratio
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(10.dp))
                  .background(if (selected) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface)
                  .border(
                    1.dp,
                    if (selected) GoldPrimary else MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(10.dp)
                  )
                  .clickable { onAspectRatioChange(ratio) }
                  .padding(10.dp)
              ) {
                Column {
                  Text(
                    text = ratio.ratioText,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (selected) GoldPrimary else MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = ratio.description,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Resolution row
          Text(
            text = "Export Resolution:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            VideoResolution.values().forEach { res ->
              val selected = selectedResolution == res
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (selected) OchreSecondary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface)
                  .border(
                    1.dp,
                    if (selected) OchreSecondary else MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                  )
                  .clickable { onResolutionChange(res) }
                  .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = res.label,
                  style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                  color = if (selected) OchreSecondary else MaterialTheme.colorScheme.onSurface
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Narration Language
          Text(
            text = "Primary Narration Language:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(Language.values().toList()) { lang ->
              val selected = selectedLanguage == lang
              FilterChip(
                selected = selected,
                onClick = { onLanguageChange(lang) },
                label = { Text("${lang.iconEmoji} ${lang.displayName}") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = GoldPrimary,
                  selectedLabelColor = Color.Black
                )
              )
            }
          }
        }
      }
    }

    // 5. Generate Button & Progress
    item {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Button(
          onClick = onGenerate,
          enabled = !isVideoGenerating,
          colors = ButtonDefaults.buttonColors(
            containerColor = GoldPrimary,
            contentColor = Color.Black
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("btn_generate_video")
        ) {
          if (isVideoGenerating) {
            CircularProgressIndicator(
              modifier = Modifier.size(24.dp),
              color = Color.Black,
              strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Synthesizing Connected Scenes ${(generationProgress * 100).toInt()}%...",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
          } else {
            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Generate Multi-Scene Film ($creditCost Credits)",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
          }
        }

        if (isVideoGenerating) {
          Spacer(modifier = Modifier.height(8.dp))
          LinearProgressIndicator(
            progress = { generationProgress },
            modifier = Modifier
              .fillMaxWidth()
              .clip(CircleShape),
            color = GoldPrimary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Runway Gen-3 Alpha is rendering scenes with character face continuity & lighting consistency.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    // 6. Active Project Scene-by-Scene Breakdown
    if (activeProject != null) {
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "Film Project: ${activeProject.title}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }
                Text(
                  text = "${activeProject.totalDurationSec}s • ${activeProject.scenes.size} Scenes Stitched • ${activeProject.aspectRatio.ratioText} • ${activeProject.resolution.label}",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              IconButton(onClick = onNavigateToEditor) {
                Icon(
                  imageVector = Icons.Default.Tune,
                  contentDescription = "Edit in Timeline",
                  tint = GoldPrimary
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Video Preview Canvas
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black),
              contentAlignment = Alignment.Center
            ) {
              Image(
                painter = painterResource(id = R.drawable.img_hero_banner),
                contentDescription = "Scene preview",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
              )
              Box(
                modifier = Modifier
                  .size(54.dp)
                  .clip(CircleShape)
                  .background(Color.Black.copy(alpha = 0.6f))
                  .border(2.dp, GoldPrimary, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.PlayArrow,
                  contentDescription = "Play Film",
                  tint = GoldPrimary,
                  modifier = Modifier.size(32.dp)
                )
              }

              // Duration tag
              Box(
                modifier = Modifier
                  .align(Alignment.BottomEnd)
                  .padding(10.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(Color.Black.copy(alpha = 0.8f))
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = "${activeProject.totalDurationSec}:00",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = Color.White
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
              text = "Connected Storyboard Scenes:",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // List of Scenes
            activeProject.scenes.forEach { scene ->
              SceneCard(
                scene = scene,
                isExpanded = expandedSceneNumber == scene.sceneNumber,
                onToggle = {
                  expandedSceneNumber = if (expandedSceneNumber == scene.sceneNumber) null else scene.sceneNumber
                }
              )
              Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = onNavigateToEditor,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.weight(1f)
              ) {
                Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = GoldPrimary)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Open Timeline Editor", color = MaterialTheme.colorScheme.onSurface)
              }

              Button(
                onClick = { /* Export */ },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                modifier = Modifier.weight(1f)
              ) {
                Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Export MP4", color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun SceneCard(
  scene: VideoScene,
  isExpanded: Boolean,
  onToggle: () -> Unit
) {
  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    shape = RoundedCornerShape(10.dp),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onToggle() }
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(24.dp)
              .clip(CircleShape)
              .background(GoldPrimary),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${scene.sceneNumber}",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = Color.Black
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = scene.title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "${scene.durationSec}s",
            style = MaterialTheme.typography.labelSmall,
            color = GoldPrimary
          )
          Spacer(modifier = Modifier.width(6.dp))
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Scene Ready",
            tint = EmeraldHausa,
            modifier = Modifier.size(16.dp)
          )
        }
      }

      if (isExpanded) {
        Spacer(modifier = Modifier.height(10.dp))

        // Visual Prompt
        Text(
          text = "Visual Direction & Camera:",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          text = "${scene.cameraAngle} — ${scene.visualPrompt}",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Continuity check
        Text(
          text = "Character Continuity:",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = IndigoTertiary
        )
        Text(
          text = scene.characterContinuity,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Narration in Hausa/Fulfulde
        Text(
          text = "Narration (Hausa):",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = GoldPrimary
        )
        Text(
          text = "\"${scene.narrationText}\"",
          style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "English Subtitle: \"${scene.englishSubtitle}\"",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Composable
fun PresetPromptChip(label: String, onSelect: () -> Unit) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(MaterialTheme.colorScheme.surface)
      .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
      .clickable { onSelect() }
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}
