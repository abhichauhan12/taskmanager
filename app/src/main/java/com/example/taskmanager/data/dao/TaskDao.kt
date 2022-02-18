package com.example.taskmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmanager.data.entities.TaskEntitiy
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask (task : TaskEntitiy)

    @Query("select * from tasksDetails  ")
    fun getAllTask() : Flow<List<TaskEntitiy>>

    @Query("select * from tasksDetails where Completed=:completed ")
    suspend fun getCompletedTask(completed : Boolean = true) : List<TaskEntitiy>

    @Query("select * from tasksDetails where Completed=:completed order by priority asc")
    suspend fun getPriorityInc(completed : Boolean = true) : List<TaskEntitiy>

    @Query("select * from tasksDetails where Completed=:completed order by date asc")
    suspend fun getDeadlineSort(completed : Boolean = true) : List<TaskEntitiy>

    @Query("select * from tasksDetails where Completed=:completed order by date asc, priority asc ")
    suspend fun getCompletedPriorityDeadlineTask(completed : Boolean = true): List<TaskEntitiy>

}