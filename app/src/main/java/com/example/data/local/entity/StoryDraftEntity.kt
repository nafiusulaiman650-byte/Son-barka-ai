package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity representing a saved local story draft with its cultural dialect narration configuration.
 */
@Entity(tableName = "story_drafts")
data class StoryDraftEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val content: String,
  val dialectId: String,
  val dialectName: String,
  val dialectRegion: String,
  val languageCode: String,
  val deliveryStyleId: String,
  val wordCount: Int,
  val estimatedDurationSec: Int,
  val updatedAt: Long = System.currentTimeMillis()
)
