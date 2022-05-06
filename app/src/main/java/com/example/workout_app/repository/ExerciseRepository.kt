package com.example.workout_app.repository

import androidx.lifecycle.LiveData
import com.example.workout_app.data.Exercise
import com.example.workout_app.model.ExerciseDao

class ExerciseRepository private constructor(
    private val exerciseDao: ExerciseDao
) {
    companion object {
        @Volatile private var instance: ExerciseRepository? = null
        fun getInstance(exerciseDao: ExerciseDao) = instance ?: synchronized(this) {
            return@synchronized instance ?: ExerciseRepository(exerciseDao).also {
                instance = it
            }
        }
    }

    fun addExercise(exercise: Exercise) {
        exerciseDao.addExercise(exercise)
    }

    fun getExercises(): LiveData<ArrayList<Exercise>> {
        return exerciseDao.getExercises()
    }

    fun getCurrentExercise(): LiveData<Int> {
        return exerciseDao.getCurrentExercise()
    }

    fun moveToNextExercise() {
        exerciseDao.moveToNextExercise()
    }

    fun returnToPreviousExercise() {
        exerciseDao.returnToPreviousExercise()
    }

    fun resetExercise() {
        exerciseDao.resetCurrentExercise()
    }

    fun setExerciseAsCompleted(index: Int) {
        exerciseDao.setExerciseAsCompleted(index)
    }
}