package com.example.taskmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmanager.data.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask (task : Task)

    @Query("SELECT * FROM tasks_table")
    fun getAllTasks() : Flow<List<Task>>

    /*@Query("DELETE FROM tasks_table WHERE task=:task")
    suspend fun deleteTask(task: Task)*/

}