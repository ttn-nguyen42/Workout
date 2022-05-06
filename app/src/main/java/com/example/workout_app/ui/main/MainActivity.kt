package com.example.workout_app.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workout_app.databinding.ActivityMainBinding
import com.example.workout_app.databinding.ActivityMainBinding.*
import com.example.workout_app.ui.bmi.BMIActivity
import com.example.workout_app.ui.exercise.ExerciseActivity
import com.example.workout_app.ui.exercise_list.ExerciseListActivity

class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = inflate(layoutInflater)
        setContentView(activityMainBinding?.root)
        buildView()
    }

    private fun buildView() {
        activityMainBinding?.startButton?.let { it ->
            it.setOnClickListener {
                goToExercise()
            }
        }
        activityMainBinding?.exerciseListButton?.let { it ->
            it.setOnClickListener {
                goToExerciseList()
            }
        }
        activityMainBinding?.bmiButton?.let { it ->
            it.setOnClickListener {
                goToBmiCalculator()
            }
        }
    }

    private fun goToExercise() {
        val exerciseIntent = Intent(this, ExerciseActivity::class.java)
        startActivity(exerciseIntent)
    }

    private fun goToExerciseList() {
        val exerciseListIntent = Intent(this, ExerciseListActivity::class.java)
        startActivity(exerciseListIntent)
    }

    private fun goToBmiCalculator() {
        val bmiIntent = Intent(this, BMIActivity::class.java)
        startActivity(bmiIntent)
    }

    override fun onDestroy() {
        activityMainBinding = null
        super.onDestroy()
    }
}