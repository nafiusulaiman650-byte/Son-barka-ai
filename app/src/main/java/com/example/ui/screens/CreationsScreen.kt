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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VideoLibrary
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
import com.example.data.local.entity.StoryDraftEntity
import com.example.model.VideoProject
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.OchreSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CreationsScreen(
  savedProjects: List<VideoProject>,
  savedDrafts: List<StoryDraftEntity> = emptyList(),
  onSelectProject: (VideoProject) -> Unit,
  onDeleteProject: (String) -> Unit,
  onLoadDraft: (StoryDraftEntity) -> Unit = {},
  onDeleteDraft: (Long) -> Unit = {},
  onNavigateToVideo: () -> Unit,
  onNavigateToDialectStoryteller: () -> Unit = {}
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Video Productions, 1: Room Story Drafts

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("creations_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Creations Library",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Your saved cinematic films, connected scenes, and Room DB story drafts.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Button(
          onClick = if (selectedTab == 0) onNavigateToVideo else onNavigateToDialectStoryteller,
          colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
        ) {
          Icon(
            imageVector = if (selectedTab == 0) Icons.Default.Movie else Icons.Default.AutoStories,
            contentDescription = null,
            tint = Color.Black
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (selectedTab == 0) "New Film" else "New Story",
            color = Color.Black,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    // 2. Filter Tabs (Films vs Room DB Drafts)
    item {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          leadingIcon = {
            Icon(Icons.Default.Movie, contentDescription = null, modifier = Modifier.size(16.dp))
          },
          label = { Text("Video Productions (${savedProjects.size})") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = GoldPrimary,
            selectedLabelColor = Color.Black
          ),
          modifier = Modifier.testTag("tab_video_productions")
        )

        FilterChip(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          leadingIcon = {
            Icon(Icons.Default.Storage, contentDescription = null, modifier = Modifier.size(16.dp))
          },
          label = { Text("Room DB Drafts (${savedDrafts.size})") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = GoldPrimary,
            selectedLabelColor = Color.Black
          ),
          modifier = Modifier.testTag("tab_room_drafts")
        )
      }
    }

    // 3. Tab 0: Video Projects List
    if (selectedTab == 0) {
      if (savedProjects.isEmpty()) {
        item {
          Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Icon(
                imageVector = Icons.Default.VideoLibrary,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
              )
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = "No saved creations yet",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "Generate a video or write a script to see your creations here.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      } else {
        items(savedProjects) { project ->
          Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().testTag("project_item_${project.id}")
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(170.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(Color.Black)
                  .clickable { onSelectProject(project) }
              ) {
                Image(
                  painter = painterResource(id = R.drawable.img_hero_banner),
                  contentDescription = null,
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Crop
                )

                Box(
                  modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .align(Alignment.Center),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = GoldPrimary,
                    modifier = Modifier.size(28.dp)
                  )
                }

                Box(
                  modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black.copy(alpha = 0.75f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "${project.totalDurationSec}s • ${project.resolution.label}",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = project.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )

              Text(
                text = "${project.scenes.size} Scenes Stitched • ${project.language.displayName} • ${project.style.displayName}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  Button(
                    onClick = { onSelectProject(project) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                  ) {
                    Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Open in Editor", color = MaterialTheme.colorScheme.onSurface)
                  }

                  IconButton(onClick = { /* Export */ }) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = "Download", tint = GoldPrimary)
                  }

                  IconButton(onClick = { /* Share */ }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                  }
                }

                IconButton(onClick = { onDeleteProject(project.id) }) {
                  Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }
          }
        }
      }
    }

    // 4. Tab 1: Room DB Story Drafts List
    if (selectedTab == 1) {
      if (savedDrafts.isEmpty()) {
        item {
          Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Icon(
                imageVector = Icons.Default.Storage,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
              )
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = "No saved Room drafts yet",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "Write your cultural stories in the Dialect Storytelling Studio and tap 'Save Draft' to persist them locally.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Spacer(modifier = Modifier.height(14.dp))
              Button(
                onClick = onNavigateToDialectStoryteller,
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
              ) {
                Text("Open Dialect Storyteller", color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      } else {
        items(savedDrafts) { draft ->
          val dateFormat = SimpleDateFormat("MMM d, yyyy • HH:mm", Locale.getDefault())
          val dateStr = dateFormat.format(Date(draft.updatedAt))

          Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().testTag("room_draft_card_${draft.id}")
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = draft.title,
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.weight(1f)
                )

                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(GoldPrimary.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = draft.dialectName,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = GoldPrimary
                  )
                }
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = draft.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
              )

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${draft.wordCount} words • ~${draft.estimatedDurationSec}s narration • $dateStr",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontSize = 11.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  Button(
                    onClick = { onLoadDraft(draft) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                  ) {
                    Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Load Draft", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                  }

                  IconButton(
                    onClick = { onDeleteDraft(draft.id) },
                    modifier = Modifier.size(32.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.Delete,
                      contentDescription = "Delete",
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
