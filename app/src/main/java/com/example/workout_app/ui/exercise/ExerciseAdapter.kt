package com.example.workout_app.ui.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout_app.R
import com.example.workout_app.data.Exercise
import com.example.workout_app.databinding.ExerciseIndicatorItemBinding

class ExerciseAdapter(private val exercises: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(private val itemBinding: ExerciseIndicatorItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(exercise: Exercise) {
            itemBinding.exerciseIndex.text = exercise.id.toString()
            if (exercise.isCompleted) {
                itemBinding.exerciseIndicatorBackground.setBackgroundResource(
                    R.drawable.rounded_indicator
                )
            } else {
                itemBinding.exerciseIndicatorBackground.setBackgroundResource(
                    R.drawable.rounded_indicator_gray
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ExerciseIndicatorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise: Exercise = exercises.elementAt(position)
        holder.bindItem(exercise)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun getLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun setDividerDecoration(recyclerView: RecyclerView) {
        val divider: DividerItemDecoration =
            DividerItemDecoration(recyclerView.context, LinearLayoutManager.HORIZONTAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                recyclerView.context,
                R.drawable.horizontal_divider
            )!!
        )
        recyclerView.addItemDecoration(divider)
    }
}