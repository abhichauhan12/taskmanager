package com.example.taskmanager.domain.repo

import android.content.Context
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.entities.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val database: TaskDatabase) {
    companion object {
        private var instance: TaskRepository? = null

        fun getInstance(context: Context): TaskRepository {
            return instance ?: synchronized(this) {
                instance = TaskRepository(database = TaskDatabase.getDatabase(context))
                instance!!
            }
        }
    }

    fun getTasks() = database.taskDao().getAllTasks()


    suspend fun updateTask(task: Task){
            database.taskDao().taskUpdate(task)
    }
  suspend fun deleteTask(id : Int){
        database.taskDao().deleteTask(id)
    }

    suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO) {
            database.taskDao().insertTask(task)
        }
    }


}