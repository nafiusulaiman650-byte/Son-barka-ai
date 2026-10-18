package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.data.local.entity.StoryDraftEntity
import com.example.model.CulturalDialect
import com.example.model.Language
import com.example.model.NarrationDeliveryStyle
import com.example.service.CulturalContentCatalog
import com.example.service.VoiceAudioEngine
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StoryDialectInputScreen(
  storyContent: String,
  selectedDialect: CulturalDialect,
  selectedDeliveryStyle: NarrationDeliveryStyle,
  audioEngine: VoiceAudioEngine,
  savedDrafts: List<StoryDraftEntity> = emptyList(),
  activeDraftId: Long? = null,
  draftNotification: String? = null,
  onStoryContentChange: (String) -> Unit,
  onDialectSelect: (CulturalDialect) -> Unit,
  onDeliveryStyleSelect: (NarrationDeliveryStyle) -> Unit,
  onPlayNarration: () -> Unit,
  onConvertToVideo: () -> Unit,
  onGenerateVisualsWithGemini: () -> Unit = {},
  onSaveDraft: (String?) -> Unit = {},
  onLoadDraft: (StoryDraftEntity) -> Unit = {},
  onDeleteDraft: (Long) -> Unit = {},
  onNewDraft: () -> Unit = {},
  onDismissNotification: () -> Unit = {},
  onBack: () -> Unit
) {
  val isPlaying by audioEngine.isPlaying.collectAsState()
  var languageFilter by remember { mutableStateOf<Language?>(null) }
  var showDialectGuide by remember { mutableStateOf(false) }
  var showSaveDraftDialog by remember { mutableStateOf(false) }
  var draftTitleInput by remember { mutableStateOf("") }
  var showDraftsList by remember { mutableStateOf(false) }

  // Statistics calculation
  val wordCount = remember(storyContent) {
    if (storyContent.isBlank()) 0 else storyContent.trim().split("\\s+".toRegex()).size
  }
  val charCount = remember(storyContent) { storyContent.length }
  val estimatedDurationSec = remember(wordCount, selectedDeliveryStyle, selectedDialect) {
    val wordsPerMinute = (130 * selectedDeliveryStyle.speed * selectedDialect.speedMultiplier).coerceIn(80f, 200f)
    ((wordCount / wordsPerMinute) * 60).toInt().coerceAtLeast(1)
  }

  val availableDialects = remember(languageFilter) {
    if (languageFilter == null) {
      CulturalContentCatalog.CULTURAL_DIALECTS
    } else {
      CulturalContentCatalog.getDialectsForLanguage(languageFilter!!)
    }
  }

  // Save Draft Dialog
  if (showSaveDraftDialog) {
    AlertDialog(
      onDismissRequest = { showSaveDraftDialog = false },
      title = {
        Text(
          text = if (activeDraftId != null) "Update Story Draft" else "Save Story Draft to Room DB",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      },
      text = {
        Column {
          Text(
            text = "Your narrative and selected dialect (${selectedDialect.name}) will be safely stored in the local SQLite Room database.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(12.dp))
          OutlinedTextField(
            value = draftTitleInput,
            onValueChange = { draftTitleInput = it },
            label = { Text("Draft Title (Optional)") },
            placeholder = { Text("e.g. Tatsuniyar Gizo da Koki") },
            modifier = Modifier.fillMaxWidth().testTag("input_draft_title"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GoldPrimary,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            onSaveDraft(draftTitleInput.ifBlank { null })
            showSaveDraftDialog = false
            draftTitleInput = ""
          },
          colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
          modifier = Modifier.testTag("btn_confirm_save_draft")
        ) {
          Text("Save to Room", color = Color.Black, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showSaveDraftDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("story_dialect_input_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 0. Notification Banner if draft was saved/loaded
    if (draftNotification != null) {
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = EmeraldHausa.copy(alpha = 0.2f)),
          border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldHausa),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().testTag("banner_draft_notification")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = EmeraldHausa,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = draftNotification,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
              )
            }
            IconButton(
              onClick = onDismissNotification,
              modifier = Modifier.size(24.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Dismiss",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }

    // 1. Top Header with Back Navigation & Room DB Status Badge
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(onClick = onBack) {
            Icon(
              imageVector = Icons.Default.ArrowBack,
              contentDescription = "Back",
              tint = MaterialTheme.colorScheme.onSurface
            )
          }
          Spacer(modifier = Modifier.width(4.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Story Narration & Dialects",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Write your African story and select authentic regional accents for oral narration",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          // Room Database Status Badge
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .border(1.dp, GoldPrimary.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
              .clickable { showDraftsList = !showDraftsList }
              .testTag("badge_room_status")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Storage,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Room DB: ${savedDrafts.size}",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = GoldPrimary,
                fontSize = 11.sp
              )
            }
          }
        }
      }
    }

    // 2. Drafts Quick Bar (Room Database Integration)
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth().testTag("card_room_drafts_panel")
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Storage,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Room Local Persistence",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = if (activeDraftId != null) "Editing saved draft #$activeDraftId" else "${savedDrafts.size} saved drafts available",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              OutlinedButton(
                onClick = onNewDraft,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                modifier = Modifier.testTag("btn_new_story_draft")
              ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("New", fontSize = 12.sp)
              }

              Button(
                onClick = {
                  if (storyContent.isNotBlank()) {
                    showSaveDraftDialog = true
                  }
                },
                enabled = storyContent.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.testTag("btn_save_story_draft")
              ) {
                Icon(Icons.Default.Save, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Save Draft", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
            }
          }

          // Expandable list of saved drafts in Room
          if (savedDrafts.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { showDraftsList = !showDraftsList },
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = if (showDraftsList) "Hide Saved Drafts ▲" else "View All Saved Drafts (${savedDrafts.size}) ▼",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = GoldPrimary
              )
              Text(
                text = "Stored locally via Room",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            AnimatedVisibility(visible = showDraftsList) {
              Column(
                modifier = Modifier.padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                savedDrafts.forEach { draft ->
                  val isCurrent = draft.id == activeDraftId
                  val dateFormat = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
                  val dateString = dateFormat.format(Date(draft.updatedAt))

                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isCurrent) GoldPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface)
                      .border(
                        1.dp,
                        if (isCurrent) GoldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        RoundedCornerShape(10.dp)
                      )
                      .padding(10.dp)
                      .testTag("draft_item_${draft.id}")
                  ) {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                          Text(
                            text = draft.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                          )
                          if (isCurrent) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                              modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(GoldPrimary)
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                              Text("ACTIVE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                          }
                        }

                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                          text = draft.content.take(80) + "...",
                          style = MaterialTheme.typography.bodySmall,
                          color = MaterialTheme.colorScheme.onSurfaceVariant,
                          maxLines = 2,
                          fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                          Box(
                            modifier = Modifier
                              .clip(RoundedCornerShape(4.dp))
                              .background(OchreSecondary.copy(alpha = 0.2f))
                              .padding(horizontal = 6.dp, vertical = 2.dp)
                          ) {
                            Text(
                              text = "${draft.dialectName} (${draft.dialectRegion})",
                              fontSize = 10.sp,
                              color = OchreSecondary,
                              fontWeight = FontWeight.SemiBold
                            )
                          }
                          Spacer(modifier = Modifier.width(8.dp))
                          Text(
                            text = "${draft.wordCount} words • ~${draft.estimatedDurationSec}s • $dateString",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 10.sp
                          )
                        }
                      }

                      Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                          onClick = { onLoadDraft(draft) },
                          modifier = Modifier.size(32.dp).testTag("btn_load_draft_${draft.id}")
                        ) {
                          Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Load Draft",
                            tint = GoldPrimary,
                            modifier = Modifier.size(18.dp)
                          )
                        }

                        IconButton(
                          onClick = { onDeleteDraft(draft.id) },
                          modifier = Modifier.size(32.dp).testTag("btn_delete_draft_${draft.id}")
                        ) {
                          Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Draft",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
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
    }

    // 3. Cultural Story Starters ("Ga Ta Ga Ta Nan")
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Cultural Story Starters:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Text(
            text = "Tap to load",
            style = MaterialTheme.typography.labelSmall,
            color = GoldPrimary
          )
        }
        Spacer(modifier = Modifier.height(6.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(CulturalContentCatalog.DIALECT_STORY_STARTERS) { starter ->
            val starterDialect = CulturalContentCatalog.getDialectById(starter.dialectId)
            Box(
              modifier = Modifier
                .width(220.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                .clickable {
                  onStoryContentChange(starter.content)
                  onDialectSelect(starterDialect)
                }
                .padding(10.dp)
            ) {
              Column {
                Text(
                  text = starter.category,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = GoldPrimary,
                  fontSize = 10.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = starter.title,
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                  color = MaterialTheme.colorScheme.onSurface,
                  maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = starter.content.take(70) + "...",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  maxLines = 2,
                  fontSize = 11.sp
                )
              }
            }
          }
        }
      }
    }

    // 4. Rich Story Content Input Box
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
                imageVector = Icons.Default.AutoStories,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Enter Storytelling Script",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
            }

            if (storyContent.isNotBlank()) {
              IconButton(
                onClick = { onStoryContentChange("") },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Clear,
                  contentDescription = "Clear text",
                  tint = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = storyContent,
            onValueChange = onStoryContentChange,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("storytelling_text_input"),
            minLines = 6,
            maxLines = 12,
            placeholder = {
              Text(
                "Rubuta labarinka a nan (misali: Tatsuniyoyi, tarihin sarakuna, ko labarin al'ada a harshen Hausa ko Fulfulde)...",
                style = MaterialTheme.typography.bodyMedium
              )
            },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GoldPrimary,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Statistics Bar
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.colorScheme.surface)
              .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Approx: ${estimatedDurationSec}s oral narration",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = GoldPrimary
              )
            }

            Text(
              text = "$wordCount words • $charCount chars",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    // 5. Cultural Dialect Selector
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
            Column {
              Text(
                text = "Select Cultural Dialect",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Narration voice, cadence and idioms match this dialect & auto-save to Room",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            IconButton(onClick = { showDialectGuide = !showDialectGuide }) {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Dialect Info",
                tint = if (showDialectGuide) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          AnimatedVisibility(visible = showDialectGuide) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GoldPrimary.copy(alpha = 0.1f))
                .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Text(
                text = "Linguistic Accuracy Guarantee:\n" +
                    "• Kananci: Standard Metropolitan tones & broadcast clarity.\n" +
                    "• Sakkwatanci: Classical Caliphate royal timbre & preserved historic lexicon.\n" +
                    "• Zazzaganci: Expressive melodic flow ideal for historical dramas & theater.\n" +
                    "• Katsinanci: Eloquent court proverbs & poetic metaphors.\n" +
                    "• Fulfulde Adamawa/Sahel: Pastoral vowel lengthening and authentic Pulaku cadence.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Filter chips for language family
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
              FilterChip(
                selected = languageFilter == null,
                onClick = { languageFilter = null },
                label = { Text("All Regional Dialects") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = GoldPrimary,
                  selectedLabelColor = Color.Black
                )
              )
            }
            item {
              FilterChip(
                selected = languageFilter == Language.HAUSA,
                onClick = { languageFilter = Language.HAUSA },
                label = { Text("🇳🇬 Hausa Dialects (5)") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = GoldPrimary,
                  selectedLabelColor = Color.Black
                )
              )
            }
            item {
              FilterChip(
                selected = languageFilter == Language.FULFULDE,
                onClick = { languageFilter = Language.FULFULDE },
                label = { Text("🌍 Fulfulde Dialects (4)") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = GoldPrimary,
                  selectedLabelColor = Color.Black
                )
              )
            }
            item {
              FilterChip(
                selected = languageFilter == Language.ENGLISH,
                onClick = { languageFilter = Language.ENGLISH },
                label = { Text("🌐 Nigerian English") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = GoldPrimary,
                  selectedLabelColor = Color.Black
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Dialect Cards
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            availableDialects.forEach { dialect ->
              val isSelected = selectedDialect.id == dialect.id

              Card(
                colors = CardDefaults.cardColors(
                  containerColor = if (isSelected) com.example.ui.theme.SurfaceHighlightDark else MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (isSelected) GoldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { onDialectSelect(dialect) }
                  .testTag("dialect_item_${dialect.id}")
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
                          .size(32.dp)
                          .clip(CircleShape)
                          .background(if (isSelected) GoldPrimary else MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                      ) {
                        Icon(
                          imageVector = if (isSelected) Icons.Default.Check else Icons.Default.GraphicEq,
                          contentDescription = null,
                          tint = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                          modifier = Modifier.size(16.dp)
                        )
                      }
                      Spacer(modifier = Modifier.width(10.dp))
                      Column {
                        Text(
                          text = dialect.name,
                          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                          color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                          text = "${dialect.nativeName} • ${dialect.region}",
                          style = MaterialTheme.typography.labelSmall,
                          color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                      }
                    }

                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (isSelected) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                      Text(
                        text = "${dialect.defaultGender} Voice",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(6.dp))

                  Text(
                    text = dialect.cadenceDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                  )

                  Spacer(modifier = Modifier.height(6.dp))

                  // Cultural sample proverb/greeting
                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(6.dp))
                      .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                      .padding(horizontal = 8.dp, vertical = 4.dp)
                  ) {
                    Text(
                      text = "Proverb: \"${dialect.proverbExample}\"",
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                      color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface,
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

    // 6. Narration Delivery Style Selector
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Oral Narration Delivery Style",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Controls pacing, vocal pitch, and traditional storytelling pauses",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(12.dp))

          // 2x3 Grid of Delivery styles
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            NarrationDeliveryStyle.values().toList().chunked(2).forEach { rowStyles ->
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                rowStyles.forEach { style ->
                  val isSelected = selectedDeliveryStyle == style
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isSelected) OchreSecondary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface)
                      .border(
                        1.dp,
                        if (isSelected) OchreSecondary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                        RoundedCornerShape(10.dp)
                      )
                      .clickable { onDeliveryStyleSelect(style) }
                      .padding(10.dp)
                      .testTag("delivery_style_${style.name}")
                  ) {
                    Column {
                      Text(
                        text = style.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (isSelected) OchreSecondary else MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp
                      )
                      Spacer(modifier = Modifier.height(2.dp))
                      Text(
                        text = style.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 10.sp,
                        maxLines = 2
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

    // 7. Action Hub: Listen Narration, Produce Video, or Gemini Visuals
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Primary Listen Button
        Button(
          onClick = onPlayNarration,
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isPlaying) OchreSecondary else GoldPrimary,
            contentColor = Color.Black
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("btn_play_dialect_narration")
        ) {
          Icon(
            imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.Black
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (isPlaying) "Stop Narration Audio" else "Listen in ${selectedDialect.name}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
          )
        }

        // Secondary Convert to Video Button
        Button(
          onClick = onConvertToVideo,
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("btn_convert_story_to_video")
        ) {
          Icon(
            imageVector = Icons.Default.MovieCreation,
            contentDescription = null,
            tint = GoldPrimary
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Produce Video with this Dialect & Story",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
        }

        // Tertiary Generate Visuals with Gemini Button
        Button(
          onClick = onGenerateVisualsWithGemini,
          colors = ButtonDefaults.buttonColors(
            containerColor = IndigoTertiary,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("btn_dialect_to_gemini_visuals")
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = Color.White
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Generate Cinematic Visuals with Gemini",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.White
          )
        }
      }
    }
  }
}
