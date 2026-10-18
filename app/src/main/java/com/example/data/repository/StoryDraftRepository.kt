package com.example.data.repository

import com.example.data.local.dao.DialectPreferenceDao
import com.example.data.local.dao.StoryDraftDao
import com.example.data.local.entity.DialectPreferenceEntity
import com.example.data.local.entity.StoryDraftEntity
import com.example.model.CulturalDialect
import com.example.model.NarrationDeliveryStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Repository abstracting local Room database persistence for story drafts and cultural dialect preferences.
 */
class StoryDraftRepository(
  private val storyDraftDao: StoryDraftDao,
  private val dialectPreferenceDao: DialectPreferenceDao
) {

  val allDrafts: Flow<List<StoryDraftEntity>> = storyDraftDao.getAllDrafts().flowOn(Dispatchers.IO)

  val dialectPreference: Flow<DialectPreferenceEntity?> =
    dialectPreferenceDao.getPreferenceFlow().flowOn(Dispatchers.IO)

  suspend fun saveDraft(
    title: String,
    content: String,
    dialect: CulturalDialect,
    deliveryStyle: NarrationDeliveryStyle,
    existingId: Long = 0
  ): Long = withContext(Dispatchers.IO) {
    val wordCount = if (content.isBlank()) 0 else content.trim().split("\\s+".toRegex()).size
    val wordsPerMin = (130 * deliveryStyle.speed * dialect.speedMultiplier).coerceIn(80f, 200f)
    val estimatedDuration = ((wordCount / wordsPerMin) * 60).toInt().coerceAtLeast(1)

    val cleanTitle = if (title.isNotBlank()) {
      title
    } else {
      val firstWords = content.trim().take(40)
      if (firstWords.isNotBlank()) "$firstWords..." else "Story Draft (${dialect.name})"
    }

    val draft = StoryDraftEntity(
      id = existingId,
      title = cleanTitle,
      content = content,
      dialectId = dialect.id,
      dialectName = dialect.name,
      dialectRegion = dialect.region,
      languageCode = dialect.language.name,
      deliveryStyleId = deliveryStyle.name,
      wordCount = wordCount,
      estimatedDurationSec = estimatedDuration,
      updatedAt = System.currentTimeMillis()
    )

    storyDraftDao.insertDraft(draft)
  }

  suspend fun deleteDraft(id: Long) = withContext(Dispatchers.IO) {
    storyDraftDao.deleteDraftById(id)
  }

  suspend fun deleteAllDrafts() = withContext(Dispatchers.IO) {
    storyDraftDao.deleteAllDrafts()
  }

  suspend fun getDraftById(id: Long): StoryDraftEntity? = withContext(Dispatchers.IO) {
    storyDraftDao.getDraftById(id)
  }

  suspend fun saveDialectPreference(
    dialect: CulturalDialect,
    deliveryStyle: NarrationDeliveryStyle
  ) = withContext(Dispatchers.IO) {
    val preference = DialectPreferenceEntity(
      id = 1,
      dialectId = dialect.id,
      dialectName = dialect.name,
      languageCode = dialect.language.name,
      deliveryStyleId = deliveryStyle.name,
      speedMultiplier = dialect.speedMultiplier,
      pitchMultiplier = dialect.pitchMultiplier,
      updatedAt = System.currentTimeMillis()
    )
    dialectPreferenceDao.savePreference(preference)
  }

  suspend fun getDialectPreferenceOnce(): DialectPreferenceEntity? = withContext(Dispatchers.IO) {
    dialectPreferenceDao.getPreference()
  }

  suspend fun getDraftCount(): Int = withContext(Dispatchers.IO) {
    storyDraftDao.getDraftCount()
  }
}
