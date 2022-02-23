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

    @Query("select * from tasks_table where Completed=:completed ")
    suspend fun getCompletedTask(completed : Boolean = true) : List<Task>

    @Query("select * from tasks_table where Completed=:completed order by priority asc")
    suspend fun getPriorityInc(completed : Boolean = true) : List<Task>

    @Query("select * from tasks_table where Completed=:completed order by time_added asc")
    suspend fun getDeadlineSort(completed : Boolean = true) : List<Task>

    @Query("select * from tasks_table where Completed=:completed order by time_added asc, priority asc ")
    suspend fun getCompletedPriorityDeadlineTask(completed : Boolean = true): List<Task>


}