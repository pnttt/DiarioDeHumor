package com.example.diariodehumor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "mood_table")
data class Mood(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mood: String,
    val date: Date
)