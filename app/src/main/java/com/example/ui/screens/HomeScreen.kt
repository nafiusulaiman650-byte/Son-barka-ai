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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.NavigationDestination
import com.example.model.ProviderStatus
import com.example.model.VideoProject
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary

@Composable
fun HomeScreen(
  savedProjects: List<VideoProject>,
  providers: List<ProviderStatus>,
  onNavigate: (NavigationDestination) -> Unit,
  onSelectProject: (VideoProject) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("home_screen"),
    contentPadding = PaddingValues(bottom = 24.dp)
  ) {
    // 1. Hero Studio Banner
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(240.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.img_hero_banner),
          contentDescription = "Futuristic African Storytelling in the Sahel",
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        // Gradient overlay
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Black.copy(alpha = 0.3f),
                  Color.Black.copy(alpha = 0.6f),
                  MaterialTheme.colorScheme.background
                )
              )
            )
        )

        // Banner Content
        Column(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(CircleShape)
              .background(GoldPrimary.copy(alpha = 0.25f))
              .border(1.dp, GoldPrimary, CircleShape)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = GoldPrimary,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "AUTHENTIC HAUSA & FULFULDE AI STUDIO",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = GoldPrimary
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Create Cinematic African Stories",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
          )

          Text(
            text = "Text-to-Video >30s, realistic Northern Nigerian voices, scene continuity & storyboards.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.85f)
          )
        }
      }
    }

    // 2. Primary Creation Actions
    item {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "Creative Studio Suites",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Choose an AI engine to start producing your masterpiece",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Grid of 2x2 cards
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          StudioActionCard(
            title = "AI Video Generator",
            subtitle = "1-Min+ Multi-Scene Films",
            icon = Icons.Default.MovieCreation,
            accentColor = GoldPrimary,
            tag = "30s-120s+",
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(NavigationDestination.VIDEO_GEN) }
          )

          StudioActionCard(
            title = "Hausa & Fulfulde Voices",
            subtitle = "Authentic Dialects & Emotions",
            icon = Icons.Default.Mic,
            accentColor = OchreSecondary,
            tag = "Neural TTS",
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(NavigationDestination.VOICE_STUDIO) }
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          StudioActionCard(
            title = "AI Story & Script",
            subtitle = "Storyboards & Dialogues",
            icon = Icons.Default.AutoStories,
            accentColor = IndigoTertiary,
            tag = "GPT-4o",
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(NavigationDestination.SCRIPT_STUDIO) }
          )

          StudioActionCard(
            title = "AI Photo Studio",
            subtitle = "Sahelian Style & Retouch",
            icon = Icons.Default.Image,
            accentColor = EmeraldHausa,
            tag = "Enhance",
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(NavigationDestination.PHOTO_EDITOR) }
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Featured Cultural Dialect Storytelling Card
        Card(
          colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SurfaceHighlightDark),
          border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigate(NavigationDestination.STORY_DIALECT_INPUT) }
            .testTag("card_story_dialect_input")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(GoldPrimary.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AutoStories,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "Dialect Storytelling Studio",
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
                    text = "DIALECTS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                    color = Color.Black,
                    fontSize = 9.sp
                  )
                }
              }
              Text(
                text = "Enter your storytelling script and select Kano, Sokoto, Zaria, or Fulfulde dialects for narration.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Icon(
              imageVector = Icons.Default.ChevronRight,
              contentDescription = "Open dialect storyteller",
              tint = GoldPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Featured Gemini Cinematic Visualizer Card
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigate(NavigationDestination.GEMINI_VISUALS) }
            .testTag("card_gemini_visuals")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(GoldPrimary.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "Gemini Visual Studio",
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
                    text = "GEMINI 3.5",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                    color = Color.Black,
                    fontSize = 9.sp
                  )
                }
              }
              Text(
                text = "Generate cinematic visual storyboards, camera motion, and lighting palettes directly from narrative text.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Icon(
              imageVector = Icons.Default.ChevronRight,
              contentDescription = "Open Gemini Visuals",
              tint = GoldPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Secondary studio card: Video Editor with Timeline
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigate(NavigationDestination.VIDEO_EDITOR) }
            .testTag("card_video_editor")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(GoldPrimary.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = null,
                tint = GoldPrimary
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "AI Video Timeline Editor",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Arrange multi-scene clips, sync voiceovers, add bilingual subtitles & transitions.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Icon(
              imageVector = Icons.Default.ChevronRight,
              contentDescription = "Open editor",
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    // 3. Cultural Heritage Quick Prompts
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Authentic Cultural Inspiration",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Tap to load authentic Hausa & Fulfulde narratives",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          contentPadding = PaddingValues(vertical = 4.dp)
        ) {
          item {
            InspirationChip(
              title = "Sarauniya Amina",
              badge = "Hausa Historical Epic",
              summary = "Queen Amina of Zazzau defending ancient city walls with royal cavalry",
              onClick = { onNavigate(NavigationDestination.SCRIPT_STUDIO) }
            )
          }
          item {
            InspirationChip(
              title = "Bayajidda da Maciji",
              badge = "Ancient Legend",
              summary = "Heroic tale of Bayajidda at the sacred Daura well",
              onClick = { onNavigate(NavigationDestination.SCRIPT_STUDIO) }
            )
          }
          item {
            InspirationChip(
              title = "Rayuwar Fulani",
              badge = "Fulfulde Folktale",
              summary = "Pastoral serenity, golden savannah pastures, melodic flute cadence",
              onClick = { onNavigate(NavigationDestination.VOICE_STUDIO) }
            )
          }
          item {
            InspirationChip(
              title = "Kano AI Metropolis",
              badge = "Sahelian Sci-Fi",
              summary = "Futuristic mud-brick architecture, solar minarets and indigo neon",
              onClick = { onNavigate(NavigationDestination.VIDEO_GEN) }
            )
          }
        }
      }
    }

    // 4. Live Multi-Provider Architecture Overview
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = EmeraldHausa,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Modular AI Cloud Architecture",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
            }

            Text(
              text = "Active & Tuned",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = EmeraldHausa
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Son Barka AI connects GPT-4o for scripts, ElevenLabs for authentic African speech, and Runway Gen-3 Alpha for multi-scene video stitching.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Provider mini pills
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            providers.take(3).forEach { p ->
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .background(MaterialTheme.colorScheme.surfaceVariant)
                  .padding(8.dp)
              ) {
                Column {
                  Text(
                    text = p.serviceType.serviceName.substringBefore(" "),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = GoldPrimary,
                    maxLines = 1
                  )
                  Text(
                    text = p.activeModel.substringBefore(" "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedButton(
            onClick = { onNavigate(NavigationDestination.SETTINGS) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldPrimary)
          ) {
            Text("Manage AI Providers & Secrets")
          }
        }
      }
    }

    // 5. Recent Creations Carousel
    if (savedProjects.isNotEmpty()) {
      item {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Recent Creations",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "View All",
              style = MaterialTheme.typography.labelLarge.copy(color = GoldPrimary),
              modifier = Modifier.clickable { onNavigate(NavigationDestination.PROJECTS) }
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          savedProjects.take(2).forEach { proj ->
            Card(
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable { onSelectProject(proj) }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = GoldPrimary,
                    modifier = Modifier.size(28.dp)
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = proj.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = "${proj.totalDurationSec}s • ${proj.scenes.size} Scenes • ${proj.language.displayName} • ${proj.resolution.label}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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

@Composable
fun StudioActionCard(
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  tag: String,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.3f)),
    shape = RoundedCornerShape(14.dp),
    modifier = modifier
      .clickable { onClick() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(accentColor.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(20.dp)
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(accentColor.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = tag,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = accentColor,
            fontSize = 10.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
      )

      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
fun InspirationChip(
  title: String,
  badge: String,
  summary: String,
  onClick: () -> Unit
) {
  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    shape = RoundedCornerShape(12.dp),
    modifier = Modifier
      .width(220.dp)
      .clickable { onClick() }
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Text(
        text = badge,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
        color = GoldPrimary,
        fontSize = 10.sp
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = summary,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2
      )
    }
  }
}
