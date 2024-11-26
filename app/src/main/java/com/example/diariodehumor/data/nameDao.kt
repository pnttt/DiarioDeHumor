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

    @Query("SELECT name FROM name_table LIMIT 1")
    fun getName(): Flow<String>

    @Query("SELECT * FROM name_table")
    suspend fun getAllNames(): List<Name>

    @Query("DELETE FROM name_table")
    suspend fun deleteAllNames()


}