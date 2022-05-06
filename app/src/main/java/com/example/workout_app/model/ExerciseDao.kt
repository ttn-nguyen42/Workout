package com.example.workout_app.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.workout_app.data.Exercise
import java.util.logging.Logger

class ExerciseDao {
    private val exerciseList: ArrayList<Exercise> = ArrayList()
    private val exercises: MutableLiveData<ArrayList<Exercise>> = MutableLiveData()
    private var currentExerciseIndex: MutableLiveData<Int> = MutableLiveData()

    init {
        exercises.value = exerciseList
        currentExerciseIndex.value = 0
    }

    fun addExercise(exercise: Exercise) {
        exerciseList.add(exercise)
        exercises.value = exerciseList
    }

    fun getExercises(): LiveData<ArrayList<Exercise>> {
        return exercises
    }

    fun getCurrentExercise(): LiveData<Int> {
        return currentExerciseIndex
    }

    fun moveToNextExercise() {
        currentExerciseIndex.value = currentExerciseIndex.value?.plus(1)
    }

    fun returnToPreviousExercise() {
        currentExerciseIndex.value = currentExerciseIndex.value?.minus(1)
    }

    fun resetCurrentExercise() {
        currentExerciseIndex.value = 0
        exerciseList.clear()
        exercises.value = exerciseList
    }

    fun setExerciseAsCompleted(index: Int) {
        val newExercise: Exercise = exerciseList[index].copy(isCompleted = true)
        exerciseList[index] = newExercise
        exercises.value = exerciseList
        Log.d("setExerciseAsCompleted", exerciseList[index].isCompleted.toString())
    }
}