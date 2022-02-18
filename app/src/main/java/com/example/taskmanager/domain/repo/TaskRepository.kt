package com.example.taskmanager.domain.repo

import android.content.Context
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.entities.TaskEntitiy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val database: TaskDatabase) {
    companion object{
        private var instance :TaskRepository?= null

        fun getInstance(context:Context) : TaskRepository{
            return instance ?: synchronized(this){
                instance = TaskRepository(database = TaskDatabase.getDatabase(context))
                instance!!
            }
        }
    }

    suspend fun getTasks(showCompleted : Boolean) =database.taskDao().getCompletedTask(showCompleted)

    suspend fun getPriorityInc(showCompleted : Boolean)=database.taskDao().getPriorityInc(showCompleted)

    suspend fun getDeadlineSort(showCompleted : Boolean)=database.taskDao().getDeadlineSort(showCompleted)

    suspend fun getCompletedPriorityDeadlineTask(showCompleted: Boolean)=database.taskDao().getCompletedPriorityDeadlineTask(showCompleted)

     suspend fun insertTask(task:TaskEntitiy){
         withContext(Dispatchers.IO){
             database.taskDao().insertTask(task)
         }
      }


}