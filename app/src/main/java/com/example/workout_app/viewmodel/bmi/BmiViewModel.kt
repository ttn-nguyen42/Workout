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

    var isMetric: Boolean = true

    fun calculateBmi() {
        if (isMetric) {
            bmiIndex = if ((weightIndex != null && weightIndex!! > 0) && (heightIndex != null && weightIndex!! > 0)) {
                weightIndex!! / ((heightIndex!! / 100.0) * (heightIndex!! / 100.0))
            } else {
                null
            }
        } else {
            bmiIndex = if ((weightIndex != null && weightIndex!! > 0) && (heightIndex != null && weightIndex!! > 0)) {
                (weightIndex!! * 703) / ((heightIndex!! * 12) * (heightIndex!! * 12))
            } else {
                null
            }
        }
    }

    fun fromMetricToEmpirical() {
        if (weightIndex != null) {
            weightIndex = weightIndex?.times(2.205)
        }
        if (heightIndex != null) {
            heightIndex = heightIndex?.div(30.48)
        }
    }

    fun fromEmpiricalToMetric() {
        if (weightIndex != null) {
            weightIndex = weightIndex?.div(2.205)
        }
        if (heightIndex != null) {
            heightIndex = heightIndex?.times(30.48)
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