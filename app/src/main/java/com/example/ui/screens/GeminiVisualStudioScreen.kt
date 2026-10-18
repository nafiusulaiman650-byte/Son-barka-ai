package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.model.CinematicSceneVisual
import com.example.model.CinematicVisualProject
import com.example.service.GeminiVisualService
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary

@Composable
fun GeminiVisualStudioScreen(
  narrativeText: String,
  cinematicStyle: String,
  aspectRatio: String,
  cameraLens: String,
  isGenerating: Boolean,
  generationStep: String,
  visualProject: CinematicVisualProject?,
  onNarrativeChange: (String) -> Unit,
  onStyleChange: (String) -> Unit,
  onAspectRatioChange: (String) -> Unit,
  onLensChange: (String) -> Unit,
  onGenerate: () -> Unit,
  onSendToVideoGenerator: (CinematicVisualProject) -> Unit
) {
  var selectedSceneIndex by remember { mutableStateOf(0) }
  var copiedPromptIndex by remember { mutableStateOf<Int?>(null) }

  val styles = listOf(
    "Sahelian Epic Cinema (35mm Panavision)",
    "Kannywood Royal Drama (Kano Palace)",
    "Fulani Pastoral Realism (Dawn Mist)",
    "Afro-Futurism Neo-Sahel (Indigo Neon)",
    "Heritage 70mm Historical Documentary"
  )

  val aspectRatios = listOf("16:9", "9:16", "2.39:1", "1:1")

  val lenses = listOf(
    "35mm Anamorphic Prime",
    "50mm f/1.2 Portrait Depth",
    "Sweeping Aerial Drone Dolly",
    "Low-Angle Heroic Pedestal"
  )

  val sampleStarters = listOf(
    "Sarauniya Amina Cavalry" to "A legendary 16th century Hausa cavalry brigade led by Queen Amina of Zazzau arriving at sunset before the towering red mud-brick fortifications of ancient Kano city, golden sand dust swirling under horse hooves, royal banners unfurling in the desert wind.",
    "Kano Indigo Dye Pits" to "Dawn light breaking across the ancient 500-year-old Kofar Mata indigo dye pits in Kano. Elder dyers in indigo-stained robes stirring the deep violet indigo vats as steam rises into the crisp morning Sahel air, surrounded by centuries of traditional mud walls.",
    "Fulani Pastoral Odyssey" to "A serene pastoral morning across the lush Adamawa savannah highlands. A Fulani youth with a carved wooden flute leads a magnificent herd of white zebu cattle through golden morning mist along the riverbank, calabash milk gourds resting under acacia shade.",
    "Sahel Afro-Futurism" to "A bustling 22nd-century Kano metropolis with towering solar-paneled mud minarets and glowing indigo neon calligraphy. Aerial sky-skiffs glide between bronze sky-bridges as citizens in luminescent traditional Babban Riga walk through holographic spice markets."
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("gemini_visual_studio_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header & Gemini API Status Badge
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Gemini Visual Studio",
              style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Generate cinematic visual content from your storytelling text",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(EmeraldHausa.copy(alpha = 0.15f))
              .border(1.dp, EmeraldHausa.copy(alpha = 0.5f), CircleShape)
              .padding(horizontal = 10.dp, vertical = 5.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = EmeraldHausa,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(5.dp))
              Text(
                text = if (GeminiVisualService.isApiKeyConfigured()) "Gemini 3.5 Active" else "Gemini Ready",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = EmeraldHausa
              )
            }
          }
        }
      }
    }

    // 2. Narrative Input Card with Starters
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.MovieCreation,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Narrative Storytelling Content",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
            }

            if (narrativeText.isNotBlank()) {
              IconButton(
                onClick = { onNarrativeChange("") },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Clear,
                  contentDescription = "Clear",
                  tint = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = narrativeText,
            onValueChange = onNarrativeChange,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("gemini_narrative_input"),
            minLines = 4,
            maxLines = 8,
            placeholder = {
              Text("Describe your African film narrative, characters, actions, traditional settings or write in Hausa/Fulfulde...")
            },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GoldPrimary,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Quick Starter Chips
          Text(
            text = "Cinematic Story Starters:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(sampleStarters) { (label, text) ->
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(MaterialTheme.colorScheme.surface)
                  .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                  .clickable { onNarrativeChange(text) }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = label,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                  color = MaterialTheme.colorScheme.onSurface
                )
              }
            }
          }
        }
      }
    }

    // 3. Cinematic Visual Controls (Style, Aspect Ratio, Camera Lens)
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Cinematic Visual Aesthetic",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Instruct Gemini on grading, lens depth, and visual composition",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Style Selector
          Text(
            text = "Visual Direction & Lighting:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(styles) { s ->
              val isSelected = cinematicStyle == s
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) GoldPrimary else MaterialTheme.colorScheme.surface)
                  .border(
                    1.dp,
                    if (isSelected) GoldPrimary else MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                  )
                  .clickable { onStyleChange(s) }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = s,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Aspect Ratio & Lens Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // Aspect Ratio
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Aspect Ratio:",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Spacer(modifier = Modifier.height(6.dp))
              Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                aspectRatios.forEach { ratio ->
                  val isSelected = aspectRatio == ratio
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(6.dp))
                      .background(if (isSelected) OchreSecondary else MaterialTheme.colorScheme.surface)
                      .border(
                        1.dp,
                        if (isSelected) OchreSecondary else MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(6.dp)
                      )
                      .clickable { onAspectRatioChange(ratio) }
                      .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = ratio,
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                      color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Camera Lens
          Text(
            text = "Camera Lens & Rig Movement:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(lenses) { l ->
              val isSelected = cameraLens == l
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) IndigoTertiary else MaterialTheme.colorScheme.surface)
                  .border(
                    1.dp,
                    if (isSelected) IndigoTertiary else MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                  )
                  .clickable { onLensChange(l) }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = l,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                )
              }
            }
          }
        }
      }
    }

    // 4. Generate Button & Progress
    item {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Button(
          onClick = onGenerate,
          enabled = !isGenerating && narrativeText.isNotBlank(),
          colors = ButtonDefaults.buttonColors(
            containerColor = GoldPrimary,
            contentColor = Color.Black
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("btn_generate_gemini_visuals")
        ) {
          if (isGenerating) {
            CircularProgressIndicator(
              modifier = Modifier.size(22.dp),
              color = Color.Black,
              strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Directing with Gemini 3.5 Flash...",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          } else {
            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Generate Cinematic Visual Content",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          }
        }

        if (isGenerating) {
          Spacer(modifier = Modifier.height(8.dp))
          LinearProgressIndicator(
            modifier = Modifier
              .fillMaxWidth()
              .clip(CircleShape),
            color = GoldPrimary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = generationStep.ifBlank { "Synthesizing visual scenes and lighting..." },
            style = MaterialTheme.typography.labelSmall,
            color = GoldPrimary
          )
        }
      }
    }

    // 5. Generated Cinematic Visual Content
    if (visualProject != null) {
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            // Master Overview Header
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "Cinematic Production Board",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(GoldPrimary)
                      .padding(horizontal = 4.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "GEMINI AI",
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                      color = Color.Black,
                      fontSize = 9.sp
                    )
                  }
                }
                Text(
                  text = "${visualProject.scenes.size} Visual Scenes • ${visualProject.aspectRatio} • ${visualProject.cameraLens}",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Button(
                onClick = { onSendToVideoGenerator(visualProject) },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
              ) {
                Icon(
                  imageVector = Icons.Default.Videocam,
                  contentDescription = null,
                  tint = Color.Black,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Produce Video", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Color Palette
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Color Grading Palette:",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Spacer(modifier = Modifier.width(8.dp))
              visualProject.colorPalette.forEach { hexColor ->
                val parsedColor = try {
                  Color(android.graphics.Color.parseColor(hexColor))
                } catch (e: Exception) {
                  GoldPrimary
                }
                Box(
                  modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(parsedColor)
                    .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Character & Clothing Continuity Sheet
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(10.dp)
            ) {
              Column {
                Text(
                  text = "Character Continuity & Costume Locking:",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = IndigoTertiary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = visualProject.characterContinuityNotes,
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Scene Tabs / Selector
            Text(
              text = "Scene Visual Storyboard Frames:",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              items(visualProject.scenes.indices.toList()) { index ->
                val scene = visualProject.scenes[index]
                val isSelected = selectedSceneIndex == index
                Box(
                  modifier = Modifier
                    .width(110.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) GoldPrimary else MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { selectedSceneIndex = index }
                    .padding(8.dp)
                ) {
                  Column {
                    Text(
                      text = "Scene ${scene.sceneNumber}",
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                      color = if (isSelected) Color.Black else GoldPrimary
                    )
                    Text(
                      text = scene.timeOffset,
                      style = MaterialTheme.typography.bodySmall,
                      color = if (isSelected) Color.Black.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant,
                      fontSize = 10.sp
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Active Scene Detail Card
            val activeScene = visualProject.scenes.getOrNull(selectedSceneIndex) ?: visualProject.scenes.first()

            Card(
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                // Visual Canvas Frame
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black),
                  contentAlignment = Alignment.Center
                ) {
                  Image(
                    painter = painterResource(id = R.drawable.img_hero_banner),
                    contentDescription = activeScene.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                  )

                  // Scene Badge
                  Box(
                    modifier = Modifier
                      .align(Alignment.TopStart)
                      .padding(8.dp)
                      .clip(RoundedCornerShape(4.dp))
                      .background(Color.Black.copy(alpha = 0.8f))
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "Scene ${activeScene.sceneNumber}: ${activeScene.timeOffset}",
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                      color = GoldPrimary
                    )
                  }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                  text = activeScene.title,
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Visual Description
                Text(
                  text = "Visual Framing & Environment:",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = activeScene.visualDescription,
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Camera & Lighting
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(6.dp))
                      .background(MaterialTheme.colorScheme.surface)
                      .padding(8.dp)
                  ) {
                    Column {
                      Text(
                        text = "Camera Motion",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = OchreSecondary
                      )
                      Text(
                        text = activeScene.cameraDirection,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 11.sp
                      )
                    }
                  }

                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(6.dp))
                      .background(MaterialTheme.colorScheme.surface)
                      .padding(8.dp)
                  ) {
                    Column {
                      Text(
                        text = "Lighting & Atmosphere",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = EmeraldHausa
                      )
                      Text(
                        text = activeScene.lightingAndAtmosphere,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 11.sp
                      )
                    }
                  }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Dialogue or Narration quote
                if (activeScene.dialogueOrNarration.isNotBlank()) {
                  Text(
                    text = "Oral Narration / Dialogue:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = GoldPrimary
                  )
                  Text(
                    text = "\"${activeScene.dialogueOrNarration}\"",
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                }

                // Veo & Runway prompt generator
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(10.dp)
                ) {
                  Column {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Text(
                        text = "Synthesized Veo & Runway Video Prompt:",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = GoldPrimary
                      )
                      IconButton(
                        onClick = { copiedPromptIndex = activeScene.sceneNumber },
                        modifier = Modifier.size(24.dp)
                      ) {
                        Icon(
                          imageVector = if (copiedPromptIndex == activeScene.sceneNumber) Icons.Default.CheckCircle else Icons.Default.ContentCopy,
                          contentDescription = "Copy prompt",
                          tint = if (copiedPromptIndex == activeScene.sceneNumber) EmeraldHausa else MaterialTheme.colorScheme.onSurfaceVariant,
                          modifier = Modifier.size(16.dp)
                        )
                      }
                    }
                    Text(
                      text = activeScene.veoPrompt,
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      fontSize = 11.sp
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
