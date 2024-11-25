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


    @Delete
    suspend fun deleteMood(mood: Mood)
}
