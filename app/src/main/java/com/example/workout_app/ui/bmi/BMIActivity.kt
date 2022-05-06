package com.example.workout_app.ui.bmi

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.R
import com.example.workout_app.databinding.ActivityBmiactivityBinding
import com.example.workout_app.utils.InjectorUtils
import com.example.workout_app.viewmodel.bmi.BmiStatus
import com.example.workout_app.viewmodel.bmi.BmiViewModel
import java.text.DecimalFormat

class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiactivityBinding? = null
    private var viewModel: BmiViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupToolbar()
        setupViewModel()
        enableCalculateButton()
        setupTextFields()
    }

    private fun setupBinding() {
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar? = binding?.bmiToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.bmi_calculator)
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, InjectorUtils.provideBmiViewModelFactory()).get(
            BmiViewModel::class.java
        )
    }

    private fun setupTextFields() {
        val weightField: EditText? = binding?.bmiWeightInput
        val heightField: EditText? = binding?.bmiHeightInput
        weightField?.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.toString().toDouble() > 1) {
                viewModel?.weightIndex = text.toString().toDouble()
                enableCalculateButton()
            } else {
                disableCalculateButton()
            }
        }
        heightField?.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.toString().toDouble() > 1) {
                viewModel?.heightIndex = text.toString().toDouble()
                enableCalculateButton()
            } else {
                disableCalculateButton()
            }
        }
        disableCalculateButton()
    }

    private fun enableCalculateButton() {
        val calculateButton: Button? = binding?.bmiCalculate
        calculateButton?.isEnabled = true
        calculateButton?.setOnClickListener {
            viewModel?.calculateBmi()
            if (viewModel?.bmiIndex != null) {
                setResult()
            } else {
                resetResult()
            }
        }
    }

    private fun disableCalculateButton() {
        val calculateButton: Button? = binding?.bmiCalculate
        calculateButton?.isEnabled = false
        calculateButton?.setOnClickListener(null)
    }

    private fun setResult() {
        val resultLayout: LinearLayout? =
            binding?.bmiResult
        val bmiIndex: TextView? = binding?.bmiIndex
        val bmiStatus: TextView? = binding?.bmiStatus
        val bmiAdvice: TextView? = binding?.bmiAdvice

        resultLayout?.visibility = View.VISIBLE

        bmiIndex?.text = DecimalFormat("##.##").format(viewModel?.bmiIndex ?: 0.0).toString()
        bmiStatus?.text = resources.getString(classifyObesityLevel().first)
        bmiAdvice?.text = resources.getString(classifyObesityLevel().second)
    }

    private fun resetResult() {
        val resultLayout: LinearLayout? = binding?.bmiResult
        resultLayout?.visibility = View.INVISIBLE
    }

    private fun classifyObesityLevel(): Pair<Int, Int> {
        when (viewModel?.determineBmiStatus()) {
            BmiStatus.ExtremelyUnderweight -> {
                return Pair(R.string.extreme_underweight, R.string.extreme_underweight_a)
            }
            BmiStatus.Underweight -> {
                return Pair(R.string.underweight, R.string.underweight_a)
            }
            BmiStatus.Healthy -> {
                return Pair(R.string.healthy, R.string.healthy_a)
            }
            BmiStatus.Obese -> {
                return Pair(R.string.obese, R.string.obese_a)
            }
            BmiStatus.ExtremelyObese -> {
                return Pair(R.string.extreme_obese, R.string.extreme_obese_a)
            }
            BmiStatus.SeverelyObese -> {
                return Pair(R.string.severe_overweight, R.string.severe_overweight_a)
            }
            else -> {
                return Pair(R.string.na, R.string.na_a)
            }
        }
    }
}