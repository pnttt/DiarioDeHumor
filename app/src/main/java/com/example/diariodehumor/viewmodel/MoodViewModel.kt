package com.example.diariodehumor.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.diariodehumor.model.Mood
import com.example.diariodehumor.data.MoodDatabase
import com.example.diariodehumor.model.Name
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoodViewModel(application: Application) : AndroidViewModel(application) {

    private val moodDao = MoodDatabase.getDatabase(application).moodDao()
    private val nameDao = MoodDatabase.getDatabase(application).nameDao()

    val allMoods: Flow<List<Mood>> = moodDao.getAllMoods()
    val name: Flow<String> = nameDao.getName()

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



    fun deleteMood(mood: Mood) {
        viewModelScope.launch {
            moodDao.deleteMood(mood)
        }
    }

    fun isNameTableEmpty(): Flow<Boolean> {
        return nameDao.getName().map {
            it.isEmpty()
        }.catch { emit(true) }
    }


    suspend fun getLastRegisteredName(): String {
        val allNames = nameDao.getAllNames()
        val validNames = allNames.filter { it.name.isNotEmpty() }
        val lastName = validNames.lastOrNull()?.name ?: ""
        return lastName
    }



    suspend fun getAllSavedNames(): List<Name> {
        val names = nameDao.getAllNames()
        return names
    }


}
