package com.example.diariodehumor.data

import androidx.room.*
import com.example.diariodehumor.model.Mood
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: Mood)

    @Query("SELECT * FROM mood_table ORDER BY date DESC")
    fun getAllMoods(): Flow<List<Mood>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertName(name: String)

    @Query("SELECT 'name' FROM mood_table")
    fun getName(): Flow<String>

    @Delete
    suspend fun deleteMood(mood: Mood)
}
