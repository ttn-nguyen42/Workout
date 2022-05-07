package com.example.workout_app.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout_app.R
import com.example.workout_app.data.History
import com.example.workout_app.databinding.HistoryListItemBinding

class HistoryAdapter(private val history: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(private val item: HistoryListItemBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(data: History) {
            item.completedCount.text = data.exerciseFinished.toString()
            item.timestampView.text = data.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyData: History = history[position]
        holder.bind(historyData)
    }

    override fun getItemCount(): Int {
        return history.size
    }

    fun getLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun setDivider(context: Context, recyclerView: RecyclerView) {
        val divider: DividerItemDecoration =
            DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                recyclerView.context,
                R.drawable.horizontal_divider
            )!!
        )
        recyclerView.addItemDecoration(divider)
    }
}