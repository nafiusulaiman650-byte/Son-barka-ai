package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.DialectPreferenceDao
import com.example.data.local.dao.StoryDraftDao
import com.example.data.local.entity.DialectPreferenceEntity
import com.example.data.local.entity.StoryDraftEntity

@Database(
  entities = [
    StoryDraftEntity::class,
    DialectPreferenceEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class SonBarkaDatabase : RoomDatabase() {

  abstract fun storyDraftDao(): StoryDraftDao
  abstract fun dialectPreferenceDao(): DialectPreferenceDao

  companion object {
    @Volatile
    private var INSTANCE: SonBarkaDatabase? = null

    fun getInstance(context: Context): SonBarkaDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          SonBarkaDatabase::class.java,
          "son_barka_studio.db"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
