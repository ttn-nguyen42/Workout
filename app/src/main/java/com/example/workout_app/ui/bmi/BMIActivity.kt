package com.example.workout_app.ui.bmi

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat
import java.util.logging.Logger

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiactivityBinding
    private lateinit var viewModel: BmiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupToolbar()
        setupViewModel()
        disableCalculateButton()
        setupTextFields()
        setupRadioGroup()
    }

    private fun setupBinding() {
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.bmiToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.bmi_calculator)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, InjectorUtils.provideBmiViewModelFactory()).get(
            BmiViewModel::class.java
        )
    }

    private fun setupTextFields() {
        val weightField: TextInputEditText = binding.bmiWeightInput
        val heightField: TextInputEditText = binding.bmiHeightInput
        if (viewModel.weightIndex != null) {
            weightField.setText(viewModel.weightIndex.toString())
        }
        weightField.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.toString().toDouble() > 1) {
                viewModel.weightIndex = text.toString().toDouble()
                enableCalculateButton()
            } else {
                disableCalculateButton()
            }
        }
        if (viewModel.heightIndex != null) {
            heightField.setText(viewModel.heightIndex.toString())
        }
        heightField.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.toString().toDouble() > 1) {
                viewModel.heightIndex = text.toString().toDouble()
                enableCalculateButton()
            } else {
                disableCalculateButton()
            }
        }
//        disableCalculateButton()
        setFieldsHints()
    }

    private fun enableCalculateButton() {
        val calculateButton: Button = binding.bmiCalculate
        if (viewModel.heightIndex != null && viewModel.weightIndex != null) {
            if (viewModel.heightIndex!! > 0 && viewModel.weightIndex!! > 0) {
                calculateButton.isEnabled = true
                calculateButton.setOnClickListener {
                    viewModel.calculateBmi()
                    if (viewModel.bmiIndex != null) {
                        setResult()
                    } else {
                        resetResult()
                    }
                }
            }
        }
    }

    private fun disableCalculateButton() {
        val calculateButton: Button = binding.bmiCalculate
        calculateButton.isEnabled = false
        calculateButton.setOnClickListener(null)
    }

    private fun setResult() {
        val resultLayout: LinearLayout =
            binding.bmiResult
        val bmiIndex: TextView = binding.bmiIndex
        val bmiStatus: TextView = binding.bmiStatus
        val bmiAdvice: TextView = binding.bmiAdvice

        resultLayout.visibility = View.VISIBLE

        bmiIndex.text = DecimalFormat("##.##").format(viewModel.bmiIndex ?: 0.0).toString()
        bmiStatus.text = resources.getString(classifyObesityLevel().first)
        bmiAdvice.text = resources.getString(classifyObesityLevel().second)
    }

    private fun resetResult() {
        val resultLayout: LinearLayout = binding.bmiResult
        resultLayout.visibility = View.INVISIBLE
    }

    private fun classifyObesityLevel(): Pair<Int, Int> {
        when (viewModel.determineBmiStatus()) {
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

    private fun setupRadioGroup() {
        val unitGroup: RadioGroup = binding.bmiViewRadioGroup
        unitGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.bmiMetric -> {
                    viewModel.isMetric = true
                    viewModel.fromEmpiricalToMetric()
                    setupTextFields()
                }
                R.id.bmiEmpirical -> {
                    viewModel.isMetric = false
                    viewModel.fromMetricToEmpirical()
                    setupTextFields()
                }
            }
        }
    }

    private fun setFieldsHints() {
        val weightLayout: TextInputLayout = binding.bmiWeightLayout
        val heightLayout: TextInputLayout = binding.bmiHeightLayout
        if (viewModel.isMetric) {
            weightLayout.hint = resources.getString(R.string.weight)
            heightLayout.hint = resources.getString(R.string.height)
        } else {
            weightLayout.hint = resources.getString(R.string.weight_i)
            heightLayout.hint = resources.getString(R.string.height_i)
        }
    }
}