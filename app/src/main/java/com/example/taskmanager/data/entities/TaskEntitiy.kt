package com.example.taskmanager.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasksDetails")
data class TaskEntitiy (

    @PrimaryKey
    @ColumnInfo(name = "task")
    val task : String,

    @ColumnInfo(name = "priority")
    val priority : Int,

    @ColumnInfo(name = "date")
    val time : String,

    @ColumnInfo(name = "Completed")
    val completed : Boolean
){
    fun getPriorityAsString(): String {
        return "Priority : $priority"
    }
}

