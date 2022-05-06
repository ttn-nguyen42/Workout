package com.example.workout_app.viewmodel.bmi

import androidx.lifecycle.ViewModel

enum class BmiStatus {
    Underweight,
    Healthy,
    Overweight,
    Obese,
    ExtremelyObese,
    ExtremelyUnderweight,
    SeverelyObese,
}

class BmiViewModel : ViewModel() {
    var weightIndex: Double? = null
    var heightIndex: Double? = null
    var bmiIndex: Double? = null

    fun calculateBmi() {
        bmiIndex = if (weightIndex != null && heightIndex != null) {
            weightIndex!! / (heightIndex!! * heightIndex!!)
        } else {
            null
        }
    }

    fun determineBmiStatus(): BmiStatus? {
        if (bmiIndex != null) {
            if (bmiIndex!! < 16.0) {
                return BmiStatus.ExtremelyUnderweight
            }
            if (bmiIndex!! < 18.5) {
                return BmiStatus.Underweight
            }
            if (bmiIndex!! < 25.0) {
                return BmiStatus.Healthy
            }
            if (bmiIndex!! < 30.0) {
                return BmiStatus.Overweight
            }
            if (bmiIndex!! < 35.0) {
                return BmiStatus.Obese
            }
            if (bmiIndex!! < 40) {
                return BmiStatus.ExtremelyObese
            }
            return BmiStatus.SeverelyObese
        } else {
            return null
        }
    }
}