package com.example.workout_app.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workout_app.data.History
import com.example.workout_app.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM ${Constants.historyTable}")
    fun getAll(): LiveData<List<History>>

    @Insert
    suspend fun insert(history: History)

    @Query("SELECT * FROM ${Constants.historyTable} WHERE ${Constants.timestamp} =:timestamp ")
    fun getHistoryOnTimestamp(timestamp: Long): LiveData<History>
}