package com.example.workout_app.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.repository.HistoryRepository

class HistoryViewModelFactory(private val historyRepository: HistoryRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(historyRepository) as T
    }
}