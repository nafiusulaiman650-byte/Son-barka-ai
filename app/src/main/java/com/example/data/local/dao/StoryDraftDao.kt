package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.StoryDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDraftDao {

  @Query("SELECT * FROM story_drafts ORDER BY updatedAt DESC")
  fun getAllDrafts(): Flow<List<StoryDraftEntity>>

  @Query("SELECT * FROM story_drafts WHERE id = :id")
  suspend fun getDraftById(id: Long): StoryDraftEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDraft(draft: StoryDraftEntity): Long

  @Update
  suspend fun updateDraft(draft: StoryDraftEntity)

  @Query("DELETE FROM story_drafts WHERE id = :id")
  suspend fun deleteDraftById(id: Long)

  @Delete
  suspend fun deleteDraft(draft: StoryDraftEntity)

  @Query("DELETE FROM story_drafts")
  suspend fun deleteAllDrafts()

  @Query("SELECT COUNT(*) FROM story_drafts")
  suspend fun getDraftCount(): Int
}
