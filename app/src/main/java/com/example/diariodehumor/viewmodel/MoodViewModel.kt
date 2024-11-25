package com.example.diariodehumor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.diariodehumor.model.Mood
import com.example.diariodehumor.data.MoodDatabase
import com.example.diariodehumor.model.Name
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MoodViewModel(application: Application) : AndroidViewModel(application) {

    // Referência ao DAO do Room
    private val moodDao = MoodDatabase.getDatabase(application).moodDao()
    private val nameDao = MoodDatabase.getDatabase(application).nameDao()

    // Fluxo de dados para observar os humores
    val allMoods: Flow<List<Mood>> = moodDao.getAllMoods()
    val name: Flow<String> = nameDao.getName()

    // Adicionar um humor
    fun addMood(mood: Mood) {
        viewModelScope.launch {
            moodDao.insertMood(mood)
        }
    }

    fun userName(name: Name) {
        viewModelScope.launch {
            nameDao.insertName(name)
        }
    }

    // Deletar um humor
    fun deleteMood(mood: Mood) {
        viewModelScope.launch {
            moodDao.deleteMood(mood)
        }
    }
}
