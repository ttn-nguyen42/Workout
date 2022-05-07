package com.example.workout_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workout_app.data.History
import com.example.workout_app.model.HistoryDao
import com.example.workout_app.utils.Constants

@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao

    companion object {
        @Volatile
        private var instance: HistoryDatabase? = null
        fun getInstance(context: Context): HistoryDatabase {
            if (instance != null) return instance!!
            synchronized(this) {
                instance = Room.databaseBuilder(context, HistoryDatabase::class.java, Constants.localDatabaseName)
                .fallbackToDestructiveMigration().build()
                return instance!!
            }
        }
    }
}