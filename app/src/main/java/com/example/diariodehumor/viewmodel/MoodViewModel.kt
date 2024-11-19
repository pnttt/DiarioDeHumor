package com.example.diariodehumor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.diariodehumor.model.Mood
import com.example.diariodehumor.data.MoodDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MoodViewModel(application: Application) : AndroidViewModel(application) {

    // Referência ao DAO do Room
    private val moodDao = MoodDatabase.getDatabase(application).moodDao()

    // Fluxo de dados para observar os humores
    val allMoods: Flow<List<Mood>> = moodDao.getAllMoods()

    // Adicionar um humor
    fun addMood(mood: Mood) {
        viewModelScope.launch {
            moodDao.insertMood(mood)
        }
    }

    // Deletar um humor
    fun deleteMood(mood: Mood) {
        viewModelScope.launch {
            moodDao.deleteMood(mood)
        }
    }
}
