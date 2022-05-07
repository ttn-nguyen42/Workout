package com.example.workout_app.viewmodel.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.repository.ExerciseRepository
import com.example.workout_app.repository.HistoryRepository

class ExerciseViewModelFactory(
    private val exerciseRepository: ExerciseRepository,
    private val historyRepository: HistoryRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExerciseViewModel(exerciseRepository, historyRepository) as T
    }
}