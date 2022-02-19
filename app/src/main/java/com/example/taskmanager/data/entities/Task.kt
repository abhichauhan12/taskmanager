package com.example.taskmanager.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.utils.TaskConstants

@Entity(tableName = "tasks_table")
data class Task(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int? = null,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "tasks")
    val task : String,

    @ColumnInfo(name = "priority")
    val priority : Int = TaskConstants.PRIORITY_DEFAULT,

    @ColumnInfo(name = "task_color")
    val taskColor : Int = TaskConstants.COLOR_DEFAULT,

    @ColumnInfo(name = "time_added")
    val time : String,

    @ColumnInfo(name = "deadline")
    val deadline : String,

    @ColumnInfo(name = "completed")
    val completed : Boolean = false

)