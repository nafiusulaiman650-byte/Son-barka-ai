package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Language
import com.example.model.VoiceProfile
import com.example.service.CulturalContentCatalog
import com.example.service.VoiceAudioEngine
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary

@Composable
fun VoiceStudioScreen(
  audioEngine: VoiceAudioEngine,
  selectedLanguage: Language,
  selectedVoice: VoiceProfile,
  customText: String,
  onLanguageSelect: (Language) -> Unit,
  onVoiceSelect: (VoiceProfile) -> Unit,
  onCustomTextChange: (String) -> Unit
) {
  val isPlaying by audioEngine.isPlaying.collectAsState()
  val activeVoiceId by audioEngine.activeVoiceId.collectAsState()
  val currentRate by audioEngine.speechRate.collectAsState()
  val currentPitch by audioEngine.speechPitch.collectAsState()

  var selectedTab by remember { mutableStateOf(0) } // 0: Voice Profiles, 1: Script Bench, 2: Dialogue Lab

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("voice_studio_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header & Quality Assurance Badge
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Authentic Voice Studio",
              style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Specialized Northern Nigerian Hausa & Fulfulde Neural Speech",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(EmeraldHausa.copy(alpha = 0.15f))
              .border(1.dp, EmeraldHausa.copy(alpha = 0.5f), CircleShape)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = null,
                tint = EmeraldHausa,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "ElevenLabs + Hardware TTS",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = EmeraldHausa
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Navigation Tabs
        TabRow(
          selectedTabIndex = selectedTab,
          containerColor = MaterialTheme.colorScheme.surfaceVariant,
          indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
              Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
              color = GoldPrimary
            )
          }
        ) {
          Tab(
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
            text = { Text("Voices & Dialects", fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 },
            text = { Text("Pronunciation Bench", fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = selectedTab == 2,
            onClick = { selectedTab = 2 },
            text = { Text("Multi-Character Lab", fontWeight = FontWeight.Bold) }
          )
        }
      }
    }

    if (selectedTab == 0) {
      // 2. Language Selector Filter Chips
      item {
        Column {
          Text(
            text = "Select Language or Dialect:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(Language.values().toList()) { lang ->
              val isSelected = selectedLanguage == lang
              FilterChip(
                selected = isSelected,
                onClick = { onLanguageSelect(lang) },
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

      // 3. Voice Profiles List
      val voices = CulturalContentCatalog.getVoicesForLanguage(selectedLanguage)
      if (voices.isEmpty()) {
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
              Text("No voices added yet for ${selectedLanguage.displayName}.")
            }
          }
        }
      } else {
        items(voices) { voice ->
          val isVoiceSelected = selectedVoice.id == voice.id
          val isThisPlaying = isPlaying && activeVoiceId == voice.id

          VoiceProfileCard(
            profile = voice,
            isSelected = isVoiceSelected,
            isPlaying = isThisPlaying,
            onSelect = { onVoiceSelect(voice) },
            onPlayToggle = {
              if (isThisPlaying) {
                audioEngine.stop()
              } else {
                onVoiceSelect(voice)
                audioEngine.previewVoice(voice)
              }
            }
          )
        }
      }

      // 4. Speech Controls (Rate & Pitch)
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Voice Delivery Controls",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Speech Rate
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("Speaking Speed", style = MaterialTheme.typography.bodyMedium)
              Text(
                text = "${String.format("%.2f", currentRate)}x",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = GoldPrimary
              )
            }
            Slider(
              value = currentRate,
              onValueChange = { audioEngine.setRate(it) },
              valueRange = 0.6f..1.6f,
              colors = SliderDefaults.colors(
                thumbColor = GoldPrimary,
                activeTrackColor = GoldPrimary
              )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Voice Pitch
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("Vocal Pitch", style = MaterialTheme.typography.bodyMedium)
              Text(
                text = "${String.format("%.2f", currentPitch)}x",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = OchreSecondary
              )
            }
            Slider(
              value = currentPitch,
              onValueChange = { audioEngine.setPitch(it) },
              valueRange = 0.6f..1.5f,
              colors = SliderDefaults.colors(
                thumbColor = OchreSecondary,
                activeTrackColor = OchreSecondary
              )
            )
          }
        }
      }
    } else if (selectedTab == 1) {
      // 5. Pronunciation Test Bench
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Test Custom Hausa / Fulfulde Script",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Enter any custom script or dialogue to test pronunciation accuracy, tone and rhythm.",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
              value = customText,
              onValueChange = onCustomTextChange,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("custom_tts_input"),
              placeholder = {
                Text("Misali: Ina maraba da ku zuwa wannan gagarumin shiri na Son Barka AI...")
              },
              minLines = 4,
              maxLines = 6,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GoldPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
              )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Button(
                onClick = {
                  if (isPlaying) {
                    audioEngine.stop()
                  } else {
                    val textToSpeak = customText.ifBlank { selectedVoice.sampleText }
                    audioEngine.previewVoice(selectedVoice, textToSpeak)
                  }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                modifier = Modifier
                  .weight(1f)
                  .testTag("btn_test_speech")
              ) {
                Icon(
                  imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                  contentDescription = null,
                  tint = Color.Black
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (isPlaying) "Stop Audio" else "Synthesize Speech",
                  color = Color.Black,
                  fontWeight = FontWeight.Bold
                )
              }

              Button(
                onClick = {
                  onCustomTextChange("Sanarwa: An fara gasar rubutu da bada labari a fadin kasar Hausa. Kowa na iya shiga domin nuna bajinta!")
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
              ) {
                Text("Sample Script", color = MaterialTheme.colorScheme.onSurface)
              }
            }
          }
        }
      }

      // Cultural Pronunciation Quality Rules
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = GoldPrimary
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Authentic Linguistic Integrity Standards",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "• Hausa Consonant Articulation: Accurate handling of hooked letters ('y, ɓ, ɗ) and tone levels.\n" +
                  "• Fulfulde Dialect Preservation: Natural vowel length ('aa', 'ee', 'oo') and noun class concord without robotic drift.\n" +
                  "• Emotional Realism: Dynamic cadence shifting between royal decrees, dramatic confrontation, and gentle folklore.\n" +
                  "• Zero False Claims: Native audio synthesis verified through local device engine and ElevenLabs Multilingual models.",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 22.sp
            )
          }
        }
      }
    } else {
      // 6. Multi-Character Dialogue Laboratory
      item {
        MultiCharacterDialogueLab(
          audioEngine = audioEngine,
          isPlaying = isPlaying
        )
      }
    }
  }
}

@Composable
fun VoiceProfileCard(
  profile: VoiceProfile,
  isSelected: Boolean,
  isPlaying: Boolean,
  onSelect: () -> Unit,
  onPlayToggle: () -> Unit
) {
  Card(
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) com.example.ui.theme.SurfaceHighlightDark else MaterialTheme.colorScheme.surfaceVariant
    ),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (isSelected) GoldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
    ),
    shape = RoundedCornerShape(14.dp),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelect() }
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(if (profile.gender == "Male") IndigoTertiary.copy(alpha = 0.2f) else OchreSecondary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.RecordVoiceOver,
              contentDescription = null,
              tint = if (profile.gender == "Male") IndigoTertiary else OchreSecondary,
              modifier = Modifier.size(24.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = profile.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              if (profile.isFeatured) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(GoldPrimary)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "FEATURED",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                    color = Color.Black,
                    fontSize = 9.sp
                  )
                }
              }
            }
            Text(
              text = "${profile.language.displayName} • ${profile.dialect} • ${profile.gender}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        IconButton(
          onClick = onPlayToggle,
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(if (isPlaying) OchreSecondary else GoldPrimary)
        ) {
          Icon(
            imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Stop" else "Play",
            tint = Color.Black
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = profile.toneDescription,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(8.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(MaterialTheme.colorScheme.surface)
          .padding(10.dp)
      ) {
        Column {
          Text(
            text = "\"${profile.sampleText}\"",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Translation: \"${profile.sampleTranslation}\"",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }
}

@Composable
fun MultiCharacterDialogueLab(
  audioEngine: VoiceAudioEngine,
  isPlaying: Boolean
) {
  var speaker1Line by remember {
    mutableStateOf("Salamu alaikum ya Amina! Yaushe za a fara daukar wannan shirin namu mai cike da tarihi?")
  }
  var speaker2Line by remember {
    mutableStateOf("Wa alaikumus salam ya Ibrahim! Da zarar an kammala zana dukkan hotuna da sautukan da suka dace, aikin zai fara ba tare da bata lokaci ba.")
  }

  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    shape = RoundedCornerShape(16.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.Chat, contentDescription = null, tint = GoldPrimary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Multi-Character Dialogue Generator",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
      }

      Text(
        text = "Generate natural back-and-forth conversations between different characters with distinct voice profiles and dialects.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Character 1 (Ibrahim - Male)
      Text(
        text = "Speaker 1: Ibrahim (Sokoto Royal - Male)",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
        color = IndigoTertiary
      )
      Spacer(modifier = Modifier.height(4.dp))
      OutlinedTextField(
        value = speaker1Line,
        onValueChange = { speaker1Line = it },
        modifier = Modifier.fillMaxWidth(),
        minLines = 2,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = IndigoTertiary,
          unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Character 2 (Amina - Female)
      Text(
        text = "Speaker 2: Amina (Kano Standard - Female)",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
        color = GoldPrimary
      )
      Spacer(modifier = Modifier.height(4.dp))
      OutlinedTextField(
        value = speaker2Line,
        onValueChange = { speaker2Line = it },
        modifier = Modifier.fillMaxWidth(),
        minLines = 2,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = GoldPrimary,
          unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
      )

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = {
            // Play speaker 1
            audioEngine.speakText(speaker1Line, Language.HAUSA, gender = "Male")
          },
          colors = ButtonDefaults.buttonColors(containerColor = IndigoTertiary),
          modifier = Modifier.weight(1f)
        ) {
          Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
          Spacer(modifier = Modifier.width(4.dp))
          Text("Play Speaker 1", color = Color.White)
        }

        Button(
          onClick = {
            // Play speaker 2
            audioEngine.speakText(speaker2Line, Language.HAUSA, gender = "Female")
          },
          colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
          modifier = Modifier.weight(1f)
        ) {
          Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.Black)
          Spacer(modifier = Modifier.width(4.dp))
          Text("Play Speaker 2", color = Color.Black, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
