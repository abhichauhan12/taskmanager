package com.example.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.data.dao.TaskDao
import com.example.taskmanager.data.entities.Task

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object{
        private val DATABASE_NAME = "task_db"
        private var database : TaskDatabase? = null

        fun getDatabase(context: Context) : TaskDatabase{
            return database ?: synchronized(this){
                database = Room.databaseBuilder(context,TaskDatabase::class.java, DATABASE_NAME).build()

                database!!
            }
        }

    }


}