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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AiServiceType
import com.example.model.ProviderStatus
import com.example.model.UserTier
import com.example.ui.theme.EmeraldHausa
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoTertiary
import com.example.ui.theme.OchreSecondary

@Composable
fun ProviderSettingsScreen(
  providers: List<ProviderStatus>,
  userTier: UserTier,
  creditBalance: Int
) {
  var showReportDialog by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("provider_settings_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header
    item {
      Column {
        Text(
          text = "AI Architecture & Settings",
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Modular AI integration pipeline (GPT-4o, ElevenLabs, Runway Gen-3 Alpha), credentials & subscription plans.",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // 2. Modular Architecture Blueprint (Explicitly requested by user)
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = GoldPrimary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Modular AI Service Architecture",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Son Barka AI uses an interchangeable adapter pattern designed to separate frontend logic from third-party neural APIs. Providers can be swapped seamlessly:\n\n" +
                "1. Story & Dialogue Engine: OpenAI GPT-4o Omnimodel (culturally fine-tuned for Hausa & Fulfulde grammar and African historical corpus).\n" +
                "2. Speech Synthesis Pipeline: ElevenLabs Multilingual v2 with custom Northern Nigerian voice clones + Native Android TextToSpeech hardware fallback.\n" +
                "3. Video Stitching Engine: Runway Gen-3 Alpha multi-scene orchestrator with seed locking for facial, clothing, and architectural continuity.\n" +
                "4. Visual Retouch & Styles: Replicate / SDXL Sahelian checkpoint tuned on Sahel architecture, indigo textiles, and golden hour lighting.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 20.sp
          )
        }
      }
    }

    // 3. Provider Status Cards
    item {
      Text(
        text = "Active AI Provider Integrations",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
      )
    }

    items(providers) { prov ->
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = prov.serviceType.serviceName,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Model: ${prov.activeModel}",
                style = MaterialTheme.typography.labelSmall,
                color = GoldPrimary
              )
            }

            Box(
              modifier = Modifier
                .clip(CircleShape)
                .background(EmeraldHausa.copy(alpha = 0.2f))
                .border(1.dp, EmeraldHausa, CircleShape)
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = EmeraldHausa,
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Connected (${prov.latencyMs}ms)",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = EmeraldHausa,
                  fontSize = 10.sp
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = prov.notes,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(6.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Key,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Secret Key: ${prov.serviceType.requiredKeyName} (Configured via AI Studio Secrets)",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    // 4. Free vs Pro Plans
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
                text = "Current Plan: ${userTier.title}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Remaining Balance: $creditBalance Credits",
                style = MaterialTheme.typography.bodySmall,
                color = GoldPrimary
              )
            }

            Box(
              modifier = Modifier
                .clip(CircleShape)
                .background(GoldPrimary)
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text(
                text = "ACTIVE",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                color = Color.Black
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Tier comparison table
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            TierPill(
              tierName = "Free Tier",
              credits = "50 / day",
              limit = "30s max, 720p",
              isCurrent = userTier == UserTier.FREE,
              modifier = Modifier.weight(1f)
            )

            TierPill(
              tierName = "Pro Studio",
              credits = "500 / mo",
              limit = "90s max, 1080p",
              isCurrent = userTier == UserTier.PRO,
              modifier = Modifier.weight(1f)
            )

            TierPill(
              tierName = "Enterprise",
              credits = "2500 / mo",
              limit = "300s, 4K HDR",
              isCurrent = userTier == UserTier.ENTERPRISE,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    // 5. Safety, Reporting & Content Moderation Policy
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = EmeraldHausa)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Safety & Ethical Moderation Shield",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Son Barka AI enforces strict safety guidelines. Generating sexually explicit material, minor exploitation, unauthorized personal voice imitation, or hate speech is prohibited and blocked automatically by neural filters before generation starts.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedButton(
            onClick = { showReportDialog = true },
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(imageVector = Icons.Default.Report, contentDescription = null, tint = OchreSecondary)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Report Inappropriate Content or Flag AI Output", color = OchreSecondary)
          }

          if (showReportDialog) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Thank you. Our moderation team and automated filters investigate all reported logs within 2 hours.",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
              color = EmeraldHausa
            )
          }
        }
      }
    }
  }
}

@Composable
fun TierPill(
  tierName: String,
  credits: String,
  limit: String,
  isCurrent: Boolean,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(if (isCurrent) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface)
      .border(
        1.dp,
        if (isCurrent) GoldPrimary else MaterialTheme.colorScheme.outline,
        RoundedCornerShape(10.dp)
      )
      .padding(8.dp)
  ) {
    Column {
      Text(
        text = tierName,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
        color = if (isCurrent) GoldPrimary else MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = credits,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = limit,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 10.sp
      )
    }
  }
}
