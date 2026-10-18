package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity representing the user's saved local cultural dialect preference for narration.
 * Uses a fixed ID of 1 to store singleton user preference.
 */
@Entity(tableName = "dialect_preferences")
data class DialectPreferenceEntity(
  @PrimaryKey
  val id: Int = 1,
  val dialectId: String,
  val dialectName: String,
  val languageCode: String,
  val deliveryStyleId: String,
  val speedMultiplier: Float = 1.0f,
  val pitchMultiplier: Float = 1.0f,
  val updatedAt: Long = System.currentTimeMillis()
)
