package com.example.workout_app.viewmodel.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.repository.ExerciseRepository

class ExerciseViewModelFactory(private val exerciseRepository: ExerciseRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExerciseViewModel(exerciseRepository) as T
    }
}