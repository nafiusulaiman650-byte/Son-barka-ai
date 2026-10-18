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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Language
import com.example.model.ScriptSceneStory
import com.example.model.ScriptStory
import com.example.model.StoryCharacter
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary

@Composable
fun ScriptStudioScreen(
  scriptConcept: String,
  scriptGenre: String,
  scriptLanguage: Language,
  isGeneratingScript: Boolean,
  currentScript: ScriptStory?,
  onConceptChange: (String) -> Unit,
  onGenreChange: (String) -> Unit,
  onLanguageChange: (Language) -> Unit,
  onGenerateScript: () -> Unit,
  onConvertToVideo: (ScriptStory) -> Unit,
  onNavigateToDialectStoryteller: () -> Unit = {}
) {
  val genres = listOf(
    "Historical Epic",
    "Hausa Drama (Kannywood)",
    "Fulani Nomadic Folktale",
    "Educational & Tech",
    "Cultural Documentary",
    "Sahelian Sci-Fi"
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("script_studio_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header
    item {
      Column {
        Text(
          text = "AI Story & Script Creator",
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Turn short ideas into complete Hausa & Fulfulde scripts, storyboards, and multi-scene dialogues.",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
          onClick = onNavigateToDialectStoryteller,
          colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(
            imageVector = Icons.Default.RecordVoiceOver,
            contentDescription = null,
            tint = Color.Black
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Enter Story & Select Cultural Dialects",
            color = Color.Black,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    // 2. Input Concept & Genre
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Story Idea or Brief",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(6.dp))

          OutlinedTextField(
            value = scriptConcept,
            onValueChange = onConceptChange,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("script_concept_input"),
            minLines = 3,
            maxLines = 5,
            placeholder = {
              Text("Describe your story concept, moral lesson, cultural background, or historical event...")
            },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = IndigoTertiary,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Language Selector
          Text(
            text = "Script Language:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listOf(Language.HAUSA, Language.FULFULDE, Language.ENGLISH, Language.ARABIC, Language.FRENCH)) { lang ->
              val selected = scriptLanguage == lang
              FilterChip(
                selected = selected,
                onClick = { onLanguageChange(lang) },
                label = { Text("${lang.iconEmoji} ${lang.displayName}") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = IndigoTertiary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Genre Selector
          Text(
            text = "Genre & Format:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(genres) { g ->
              val isSelected = scriptGenre == g
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) GoldPrimary else MaterialTheme.colorScheme.surface)
                  .border(1.dp, if (isSelected) GoldPrimary else MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                  .clickable { onGenreChange(g) }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = g,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                  color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = onGenerateScript,
            enabled = !isGeneratingScript,
            colors = ButtonDefaults.buttonColors(containerColor = IndigoTertiary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("btn_generate_script")
          ) {
            if (isGeneratingScript) {
              CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Crafting Storyboard & Dialect...", color = Color.White)
            } else {
              Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Generate Script & Storyboard", color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 3. Generated Storyboard Output
    if (currentScript != null) {
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = androidx.compose.foundation.BorderStroke(1.dp, IndigoTertiary.copy(alpha = 0.4f)),
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
                Text(
                  text = currentScript.title,
                  style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "${currentScript.genre} • ${currentScript.language.displayName} • ${currentScript.scenes.size} Scenes",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Button(
                onClick = { onConvertToVideo(currentScript) },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
              ) {
                Icon(imageVector = Icons.Default.MovieCreation, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Produce Video", color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = currentScript.logline,
              style = MaterialTheme.typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Characters Section
            Text(
              text = "Characters & Costume Continuity:",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              currentScript.characters.forEach { char ->
                CharacterCard(character = char, modifier = Modifier.weight(1f))
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Scene by scene breakdown
            Text(
              text = "Scene Storyboard & Dialogue:",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            currentScript.scenes.forEach { scene ->
              ScriptSceneCard(scene = scene)
              Spacer(modifier = Modifier.height(10.dp))
            }
          }
        }
      }
    }
  }
}

@Composable
fun CharacterCard(character: StoryCharacter, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .padding(10.dp)
  ) {
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Person,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = character.name,
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
      }
      Text(
        text = character.role,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = character.costumeDescription,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 3
      )
    }
  }
}

@Composable
fun ScriptSceneCard(scene: ScriptSceneStory) {
  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    shape = RoundedCornerShape(10.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Scene ${scene.sceneNumber}: ${scene.location}",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = GoldPrimary
        )
        Text(
          text = scene.time,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = "Visual Direction: ${scene.visualAction}",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Dialogues
      scene.dialogues.forEach { dlg ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
        ) {
          Column {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = dlg.speaker,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = IndigoTertiary
              )
              Text(
                text = dlg.emotion,
                style = MaterialTheme.typography.labelSmall,
                color = OchreSecondary
              )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = dlg.textNative,
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Translation: ${dlg.textEnglish}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }
  }
}
