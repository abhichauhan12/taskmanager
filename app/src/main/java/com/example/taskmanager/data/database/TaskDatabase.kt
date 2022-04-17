package com.example.taskmanager.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmanager.data.database.dao.TaskDao
import com.example.taskmanager.data.database.entities.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = true,
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}