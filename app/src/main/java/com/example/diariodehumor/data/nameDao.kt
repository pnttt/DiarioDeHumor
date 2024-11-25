package com.example.diariodehumor.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diariodehumor.model.Name
import kotlinx.coroutines.flow.Flow

@Dao
interface nameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertName(name: Name)

    @Query("SELECT name FROM name_table")
    fun getName(): Flow<String>

    // @Query("SELECT name FROM name_table")
    // fun getName(): Flow<String>

}