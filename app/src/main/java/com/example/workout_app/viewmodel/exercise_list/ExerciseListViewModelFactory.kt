package com.example.workout_app.viewmodel.exercise_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.repository.ExerciseRepository
import com.example.workout_app.viewmodel.exercise.ExerciseViewModel

class ExerciseListViewModelFactory(private val exerciseRepository: ExerciseRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExerciseListViewModel(exerciseRepository) as T
    }

}