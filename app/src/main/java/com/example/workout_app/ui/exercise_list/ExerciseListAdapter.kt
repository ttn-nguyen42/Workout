package com.example.workout_app.ui.exercise_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout_app.data.Exercise
import com.example.workout_app.databinding.ExerciseListItemBinding

class ExerciseListAdapter(private val exercises: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder>() {
    inner class ExerciseListViewHolder(private val itemBinding: ExerciseListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(exercise: Exercise) {
            itemBinding.exerciseItemName.text = exercise.name
            itemBinding.exerciseItemStatus.text = exercise.isCompleted.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder {
        return ExerciseListViewHolder(
            ExerciseListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseListViewHolder, position: Int) {
        val exercise: Exercise = exercises.elementAt(position)
        holder.bindItem(exercise)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun getLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}