package com.example.taskmanager.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.data.database.dao.TaskDao
import com.example.taskmanager.data.database.entities.Task

@Database(
    entities = [Task::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}