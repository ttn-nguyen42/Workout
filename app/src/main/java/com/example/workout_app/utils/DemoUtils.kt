package com.example.workout_app.utils

import android.app.Activity
import com.example.workout_app.R
import com.example.workout_app.data.Exercise
import com.example.workout_app.repository.ExerciseRepository

object DemoUtils {
    fun fillUpExercises(exerciseRepository: ExerciseRepository) {
        exerciseRepository.addExercise(
            Exercise(
                0,
                "Jumping Jacks",
                R.drawable.ic_jumping_jacks,
                false
            )
        )
        exerciseRepository.addExercise(Exercise(1, "Wall Sit", R.drawable.ic_wall_sit, false))
        exerciseRepository.addExercise(Exercise(2, "Push Up", R.drawable.ic_push_up, false))
        exerciseRepository.addExercise(
            Exercise(3, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false)
        )
        exerciseRepository.addExercise(
            Exercise(
                4,
                "Step-Up onto Chair",
                R.drawable.ic_step_up_onto_chair,
                false,
            )
        )
        exerciseRepository.addExercise(Exercise(5, "Squat", R.drawable.ic_squat, false))
        exerciseRepository.addExercise(
            Exercise(
                6,
                "Tricep Dip On Chair",
                R.drawable.ic_triceps_dip_on_chair,
                false,
            )
        )
        exerciseRepository.addExercise(Exercise(7, "Plank", R.drawable.ic_plank, false))
        exerciseRepository.addExercise(
            Exercise(
                8, "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place,
                false,
            )
        )
        exerciseRepository.addExercise(Exercise(9, "Lunges", R.drawable.ic_lunge, false))
        exerciseRepository.addExercise(
            Exercise(
                10,
                "Push up and Rotation",
                R.drawable.ic_push_up_and_rotation,
                false,
            )
        )
        exerciseRepository.addExercise(Exercise(11, "Side Plank", R.drawable.ic_side_plank, false))
    }
}