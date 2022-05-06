package com.example.workout_app.viewmodel.exercise_list

import androidx.lifecycle.ViewModel
import com.example.workout_app.repository.ExerciseRepository
import com.example.workout_app.utils.DemoUtils

class ExerciseListViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    fun getExercises() = exerciseRepository.getExercises().value
    fun getListSize() = getExercises()?.size

    fun fillUpExercise() {
        if (getListSize() == 0) {
            DemoUtils.fillUpExercises(exerciseRepository)
        }
    }

}