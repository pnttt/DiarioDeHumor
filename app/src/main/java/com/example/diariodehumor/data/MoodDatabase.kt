package com.example.diariodehumor.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diariodehumor.model.Mood
import com.example.diariodehumor.model.Name

@Database(entities = [Mood::class, Name::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class) // Adicione esta anotação
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    abstract fun nameDao(): nameDao

    companion object {
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getDatabase(context: Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "mood_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}
