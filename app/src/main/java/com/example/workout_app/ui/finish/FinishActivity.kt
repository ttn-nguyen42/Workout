package com.example.workout_app.ui.finish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.workout_app.databinding.ActivityFinishBinding
import com.example.workout_app.ui.main.MainActivity

class FinishActivity : AppCompatActivity() {
    private var finishBinding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setContentView(finishBinding?.root)
        init()
    }

    private fun init() {
        setupButton()
    }

    private fun setupBinding() {
        finishBinding = ActivityFinishBinding.inflate(layoutInflater)
    }

    private fun setupButton() {
        finishBinding?.finishButton?.setOnClickListener {
            backToStart()
        }
    }

    private fun backToStart() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}