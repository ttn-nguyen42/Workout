package com.example.workout_app.ui.exercise_list

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.R
import com.example.workout_app.databinding.ActivityExerciseListBinding
import com.example.workout_app.utils.InjectorUtils
import com.example.workout_app.viewmodel.exercise_list.ExerciseListViewModel

class ExerciseListActivity : AppCompatActivity() {
    private var activity: ActivityExerciseListBinding? = null

    private var viewModel: ExerciseListViewModel? = null

    private var exerciseListAdapter: ExerciseListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupToolbar()
        setupViewModel()
        setupRecyclerView()
    }

    override fun onDestroy() {
        activity = null
        viewModel = null
        super.onDestroy()
    }

    private fun setupBinding() {
        activity = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(activity?.root)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar? = activity?.exerciseListToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.exercise_list)
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, InjectorUtils.provideExerciseListViewModelFactory()).get(
                ExerciseListViewModel::class.java
            )
        viewModel?.fillUpExercise()
    }

    private fun setupRecyclerView() {
        exerciseListAdapter = viewModel!!.getExercises()?.let { ExerciseListAdapter(it) }
        activity?.exerciseRecyclerView?.adapter = exerciseListAdapter
        activity?.exerciseRecyclerView?.layoutManager = exerciseListAdapter?.getLayoutManager(this)
    }
}