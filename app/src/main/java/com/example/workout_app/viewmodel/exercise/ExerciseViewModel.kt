package com.example.workout_app.viewmodel.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.workout_app.data.Exercise
import com.example.workout_app.data.History
import com.example.workout_app.repository.ExerciseRepository
import com.example.workout_app.repository.HistoryRepository
import com.example.workout_app.utils.DemoUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository, private val historyRepository: HistoryRepository): ViewModel() {
    fun getExercises(): LiveData<ArrayList<Exercise>> = exerciseRepository.getExercises()
    fun addExercise(exercise: Exercise) = exerciseRepository.addExercise(exercise)
    fun getCurrentExercise(): LiveData<Int> = exerciseRepository.getCurrentExercise()
    fun moveToNextExercise() = exerciseRepository.moveToNextExercise()
    fun returnToPreviousExercise() = exerciseRepository.returnToPreviousExercise()
    fun resetExercise() = exerciseRepository.resetExercise()

    var numberOfFinishedExercises: Int = 0

    fun fillUpExercises() {
        if (getExercises().value?.isEmpty() == true) {
            DemoUtils.fillUpExercises(exerciseRepository)
        }
    }

    fun setExerciseAsCompleted(index: Int) {
        exerciseRepository.setExerciseAsCompleted(index)
    }

    fun addHistory() {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy aa hh:mm:ss", Locale.ENGLISH)
        val now: Date = Calendar.getInstance().time
        val date: String = dateFormat.format(now)
        val history = History(date, numberOfFinishedExercises)
        historyRepository.addHistory(history)
    }
}