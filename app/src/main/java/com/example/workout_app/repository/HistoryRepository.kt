package com.example.workout_app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.data.History
import com.example.workout_app.database.HistoryDatabase
import com.example.workout_app.model.ExerciseDao
import com.example.workout_app.model.HistoryDao
import com.example.workout_app.viewmodel.history.HistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HistoryRepository private constructor(private val historyDao: HistoryDao) {
    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(historyDao: HistoryDao) = instance ?: synchronized(this) {
            instance ?: HistoryRepository(historyDao).also {
                instance = it
            }
        }
    }

    fun getHistoryDao(): LiveData<List<History>> = historyDao.getAll()

    fun addHistory(history: History) {
        CoroutineScope(IO).launch {
            historyDao.insert(history)
        }
    }

    fun findHistoryByTimestamp(timestamp: Long): LiveData<History> {
        return historyDao.getHistoryOnTimestamp(timestamp)
    }

}