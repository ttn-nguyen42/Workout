package com.example.workout_app.viewmodel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.workout_app.data.History
import com.example.workout_app.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(private val historyRepository: HistoryRepository): ViewModel() {
    lateinit var history: LiveData<List<History>>

    fun init() {
        getHistoryFromLocal()
    }

    private fun getHistoryFromLocal() {
        history = historyRepository.getHistoryDao()
    }
}