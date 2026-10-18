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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.model.PhotoEditJob
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary

@Composable
fun PhotoEditorScreen(
  photoPrompt: String,
  photoStylePreset: String,
  preserveFace: Boolean,
  enhancementStrength: Float,
  isProcessing: Boolean,
  photoHistory: List<PhotoEditJob>,
  onPromptChange: (String) -> Unit,
  onPresetChange: (String) -> Unit,
  onPreserveFaceChange: (Boolean) -> Unit,
  onEnhancementStrengthChange: (Float) -> Unit,
  onProcessPhoto: () -> Unit
) {
  var showAfterComparison by remember { mutableStateOf(true) }

  val presets = listOf(
    "Nollywood 35mm Film",
    "Kano Indigo Royal",
    "Sahelian Golden Hour",
    "Afro-Futurism Neo-Sahel",
    "Studio Portrait Glow",
    "Vintage Celluloid"
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("photo_editor_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header
    item {
      Column {
        Text(
          text = "AI Photo Studio",
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Neural portrait enhancement, Sahelian aesthetic filters, and background replacement.",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // 2. Before / After Comparison Canvas
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(230.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(Color.Black),
            contentAlignment = Alignment.Center
          ) {
            Image(
              painter = painterResource(
                id = if (showAfterComparison) R.drawable.img_hero_banner else R.drawable.ic_son_barka_icon
              ),
              contentDescription = "Photo canvas",
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.Crop
            )

            // Badge for Before / After
            Box(
              modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.Black.copy(alpha = 0.75f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = if (showAfterComparison) "✨ AI Enhanced (After)" else "📷 Original (Before)",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = if (showAfterComparison) GoldPrimary else Color.White
              )
            }

            // Quick Toggle Button
            Button(
              onClick = { showAfterComparison = !showAfterComparison },
              colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.7f)),
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
            ) {
              Icon(imageVector = Icons.Default.Compare, contentDescription = null, tint = GoldPrimary)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (showAfterComparison) "View Before" else "View After",
                color = GoldPrimary,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
              )
            }
          }
        }
      }
    }

    // 3. Prompt & Style Controls
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Describe Changes & Atmosphere",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )

          Spacer(modifier = Modifier.height(6.dp))

          OutlinedTextField(
            value = photoPrompt,
            onValueChange = onPromptChange,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("photo_prompt_input"),
            minLines = 2,
            maxLines = 4,
            placeholder = {
              Text("E.g., Add Sahelian golden hour sunset, replace background with Kano city gate...")
            },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = EmeraldHausa,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Presets
          Text(
            text = "Style Presets:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(presets) { preset ->
              val isSelected = photoStylePreset == preset
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) EmeraldHausa else MaterialTheme.colorScheme.surface)
                  .border(1.dp, if (isSelected) EmeraldHausa else MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                  .clickable { onPresetChange(preset) }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = preset,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                  color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Face Geometry Preservation Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Face, contentDescription = null, tint = GoldPrimary)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Preserve Original Face Features",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "Maintains facial likeness, eyes, and skin undertones",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Switch(
              checked = preserveFace,
              onCheckedChange = onPreserveFaceChange,
              colors = SwitchDefaults.colors(
                checkedThumbColor = GoldPrimary,
                checkedTrackColor = GoldPrimary.copy(alpha = 0.3f)
              )
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Enhancement Strength Slider
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Enhancement Strength", style = MaterialTheme.typography.bodyMedium)
            Text(
              text = "${(enhancementStrength * 100).toInt()}%",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = EmeraldHausa
            )
          }
          Slider(
            value = enhancementStrength,
            onValueChange = onEnhancementStrengthChange,
            valueRange = 0.1f..1.0f,
            colors = SliderDefaults.colors(
              thumbColor = EmeraldHausa,
              activeTrackColor = EmeraldHausa
            )
          )

          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = onProcessPhoto,
            enabled = !isProcessing,
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldHausa),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("btn_process_photo")
          ) {
            if (isProcessing) {
              CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.Black)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Rendering Neural Changes...", color = Color.Black, fontWeight = FontWeight.Bold)
            } else {
              Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color.Black)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Apply AI Photo Edit", color = Color.Black, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 4. History
    if (photoHistory.isNotEmpty()) {
      item {
        Text(
          text = "Recent Photo Jobs",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
      }

      items(photoHistory) { job ->
        Card(
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Image, contentDescription = null, tint = EmeraldHausa)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = job.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "${job.presetName} • ${job.status}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            IconButton(onClick = { /* Export */ }) {
              Icon(imageVector = Icons.Default.Download, contentDescription = "Download", tint = GoldPrimary)
            }
          }
        }
      }
    }
  }
}
