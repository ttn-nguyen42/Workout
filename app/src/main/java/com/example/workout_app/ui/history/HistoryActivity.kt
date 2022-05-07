package com.example.workout_app.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.R
import com.example.workout_app.databinding.ActivityHistoryBinding
import com.example.workout_app.utils.InjectorUtils
import com.example.workout_app.viewmodel.history.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupActionBar()
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupBinding() {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupActionBar() {
        val toolbar: Toolbar = binding.historyToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            InjectorUtils.provideHistoryViewModelFactory(this),
        ).get(HistoryViewModel::class.java)
    }

    private fun setupRecyclerView() {
        viewModel.init()
        viewModel.history.observe(this) {
            if (it.isEmpty()) {
                binding.emptyView.visibility = View.VISIBLE
                binding.historyView.visibility = View.INVISIBLE
            } else {
                binding.emptyView.visibility = View.INVISIBLE
                binding.historyView.visibility = View.VISIBLE

                adapter = HistoryAdapter(it)
                binding.historyView.adapter = adapter
                binding.historyView.layoutManager = adapter.getLayoutManager(this)
                adapter.setDivider(this, binding.historyView)
            }
        }
    }
}