package com.example.diariodehumor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "name_table")
data class Name(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
