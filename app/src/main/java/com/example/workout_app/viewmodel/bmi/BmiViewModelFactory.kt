package com.example.workout_app.viewmodel.bmi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BmiViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BmiViewModel() as T
    }
}