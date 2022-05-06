package com.example.workout_app.utils

import com.example.workout_app.database.ExerciseDatabase
import com.example.workout_app.repository.ExerciseRepository
import com.example.workout_app.viewmodel.bmi.BmiViewModelFactory
import com.example.workout_app.viewmodel.exercise.ExerciseViewModelFactory
import com.example.workout_app.viewmodel.exercise_list.ExerciseListViewModelFactory

object InjectorUtils {
    fun provideExerciseViewModelFactory(): ExerciseViewModelFactory {
        val exerciseDatabase: ExerciseDatabase = ExerciseDatabase.getInstance()
        val exerciseRepository: ExerciseRepository =
            ExerciseRepository.getInstance(exerciseDao = exerciseDatabase.exerciseDao)
        return ExerciseViewModelFactory(exerciseRepository)
    }

    fun provideExerciseListViewModelFactory(): ExerciseListViewModelFactory {
        val exerciseDatabase: ExerciseDatabase = ExerciseDatabase.getInstance()
        val exerciseRepository: ExerciseRepository = ExerciseRepository.getInstance(exerciseDao = exerciseDatabase.exerciseDao)
        return ExerciseListViewModelFactory(exerciseRepository)
    }

    fun provideBmiViewModelFactory(): BmiViewModelFactory {
        return BmiViewModelFactory()
    }
}