package com.example.workout_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workout_app.utils.Constants

@Entity(tableName = Constants.historyTable)
data class History(
    @PrimaryKey
    @ColumnInfo(name = Constants.timestamp)
    val date: String,
    @ColumnInfo(name = Constants.numberOfExerciseFinished)
    val exerciseFinished: Int?,
)
