package com.example.taskmanager.data.dao

import androidx.room.*
import com.example.taskmanager.data.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask (task : Task)

    @Query("SELECT * FROM tasks_table")
    fun getAllTasks() : Flow<List<Task>>

    @Update
    suspend fun taskUpdate(task: Task)

    @Query("DELETE FROM tasks_table WHERE id=:id ")
    suspend fun deleteTask(id : Int)


}