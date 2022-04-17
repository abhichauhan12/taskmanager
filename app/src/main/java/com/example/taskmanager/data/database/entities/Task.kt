@file:Suppress("HasPlatformType")

package com.example.taskmanager.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amplifyframework.datastore.generated.model.TaskData
import com.example.taskmanager.utils.TaskConstants
import com.example.taskmanager.utils.getFormattedDate
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tasks_table")
data class Task(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "tasks")
    val task: String,

    @ColumnInfo(name = "priority")
    val priority: Int = TaskConstants.PRIORITY_DEFAULT,

    @ColumnInfo(name = "task_color")
    var taskColor: Int = TaskConstants.COLOR_DEFAULT,

    @ColumnInfo(name = "time_added")
    val time: String,

    @ColumnInfo(name = "deadline")
    val deadline: Long,

    @ColumnInfo(name = "completed")
    val completed: Boolean = false,
) : Parcelable {

    fun getDate() = getFormattedDate(deadline)

    fun toTaskData() =
        TaskData.builder()
            .name(title)
            .description(task).taskColor(taskColor).priority(priority).completed(completed)
            .deadline(deadline.toInt()).id(id.toString()).time(time).build()

    companion object {
        fun fromTaskData(taskData: TaskData) =
            Task(
                id = taskData.id.toInt(),
                title = taskData.name,
                task = taskData.description,
                priority = taskData.priority,
                taskColor = taskData.taskColor,
                time = taskData.time,
                deadline = taskData.deadline.toLong(),
                completed = taskData.completed
            )
    }
}