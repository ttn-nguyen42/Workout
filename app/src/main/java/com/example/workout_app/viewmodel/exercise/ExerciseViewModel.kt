package com.example.workout_app.viewmodel.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.workout_app.data.Exercise
import com.example.workout_app.repository.ExerciseRepository
import com.example.workout_app.utils.DemoUtils

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository): ViewModel() {
    fun getExercises(): LiveData<ArrayList<Exercise>> = exerciseRepository.getExercises()
    fun addExercise(exercise: Exercise) = exerciseRepository.addExercise(exercise)
    fun getCurrentExercise(): LiveData<Int> = exerciseRepository.getCurrentExercise()
    fun moveToNextExercise() = exerciseRepository.moveToNextExercise()
    fun returnToPreviousExercise() = exerciseRepository.returnToPreviousExercise()
    fun resetExercise() = exerciseRepository.resetExercise()
    fun fillUpExercises() {
        if (getExercises().value?.isEmpty() == true) {
            DemoUtils.fillUpExercises(exerciseRepository)
        }
    }
    fun setExerciseAsCompleted(index: Int) {
        exerciseRepository.setExerciseAsCompleted(index)
    }
}