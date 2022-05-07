package com.example.workout_app.utils

import android.content.Context
import androidx.room.Room
import com.example.workout_app.database.ExerciseDatabase
import com.example.workout_app.database.HistoryDatabase
import com.example.workout_app.repository.ExerciseRepository
import com.example.workout_app.repository.HistoryRepository
import com.example.workout_app.viewmodel.bmi.BmiViewModelFactory
import com.example.workout_app.viewmodel.exercise.ExerciseViewModelFactory
import com.example.workout_app.viewmodel.exercise_list.ExerciseListViewModelFactory
import com.example.workout_app.viewmodel.history.HistoryViewModelFactory

object InjectorUtils {
    fun provideExerciseViewModelFactory(context: Context): ExerciseViewModelFactory {
        val historyDatabase: HistoryDatabase = HistoryDatabase.getInstance(context)
        val historyRepository: HistoryRepository =
            HistoryRepository.getInstance(historyDatabase.getHistoryDao())

        val exerciseDatabase: ExerciseDatabase = ExerciseDatabase.getInstance()
        val exerciseRepository: ExerciseRepository =
            ExerciseRepository.getInstance(exerciseDao = exerciseDatabase.exerciseDao)
        return ExerciseViewModelFactory(exerciseRepository, historyRepository)
    }

    fun provideExerciseListViewModelFactory(): ExerciseListViewModelFactory {
        val exerciseDatabase: ExerciseDatabase = ExerciseDatabase.getInstance()
        val exerciseRepository: ExerciseRepository =
            ExerciseRepository.getInstance(exerciseDao = exerciseDatabase.exerciseDao)
        return ExerciseListViewModelFactory(exerciseRepository)
    }

    fun provideBmiViewModelFactory(): BmiViewModelFactory {
        return BmiViewModelFactory()
    }

    fun provideHistoryViewModelFactory(context: Context): HistoryViewModelFactory {
        val historyDatabase: HistoryDatabase = HistoryDatabase.getInstance(context)
        val historyRepository: HistoryRepository =
            HistoryRepository.getInstance(historyDatabase.getHistoryDao())
        return HistoryViewModelFactory(historyRepository)
    }
}