package com.example.workout_app.database

import com.example.workout_app.model.ExerciseDao

class ExerciseDatabase private constructor() {
    val exerciseDao: ExerciseDao = ExerciseDao()
    companion object {
        @Volatile private var instance: ExerciseDatabase? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ExerciseDatabase().also {
                instance = it
            }
        }
    }
}