package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.DialectPreferenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DialectPreferenceDao {

  @Query("SELECT * FROM dialect_preferences WHERE id = 1")
  fun getPreferenceFlow(): Flow<DialectPreferenceEntity?>

  @Query("SELECT * FROM dialect_preferences WHERE id = 1")
  suspend fun getPreference(): DialectPreferenceEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun savePreference(preference: DialectPreferenceEntity)

  @Query("DELETE FROM dialect_preferences WHERE id = 1")
  suspend fun clearPreference()
}
