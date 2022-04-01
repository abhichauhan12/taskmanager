package com.example.taskmanager.domain.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.taskmanager.data.database.TaskDatabase
import com.example.taskmanager.data.database.entities.Task
import com.example.taskmanager.utils.SettingsPrefsConstants.showCompletedKey
import com.example.taskmanager.utils.SettingsPrefsConstants.sortByDeadlineKey
import com.example.taskmanager.utils.SettingsPrefsConstants.sortByPriorityKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val database: TaskDatabase,
    private val dataStore: DataStore<Preferences>
) {

    val showCompleted = dataStore.data.map {
        it[showCompletedKey] ?: false
    }

    val sortByPriority = dataStore.data.map {
        it[sortByPriorityKey] ?: false
    }

    val sortByDeadline = dataStore.data.map {
        it[sortByDeadlineKey] ?: false
    }

    suspend fun updateShowCompleted(showCompleted: Boolean) {
        dataStore.edit {
            it[showCompletedKey] = showCompleted
        }
    }

    suspend fun updateShowPriority(sortByPriority: Boolean) {
        dataStore.edit {
            it[sortByPriorityKey] = sortByPriority
        }
    }

    suspend fun updateShowDeadline(sortByDeadline: Boolean) {
        dataStore.edit {
            it[sortByDeadlineKey] = sortByDeadline
        }
    }


    fun getTasks() = database.taskDao().getAllTasks()


    suspend fun updateTask(task: Task) {
        database.taskDao().taskUpdate(task)
    }

    suspend fun deleteTask(id: Int) {
        database.taskDao().deleteTask(id)
    }

    suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO) {
            database.taskDao().insertTask(task)
        }
    }

    suspend fun getTasks(showCompleted: Boolean) =
        database.taskDao().getCompletedTask(showCompleted)

    suspend fun getPriorityInc(showCompleted: Boolean) =
        database.taskDao().getPriorityInc(showCompleted)

    suspend fun getDeadlineSort(showCompleted: Boolean) =
        database.taskDao().getDeadlineSort(showCompleted)

    suspend fun getCompletedPriorityDeadlineTask(showCompleted: Boolean) =
        database.taskDao().getCompletedPriorityDeadlineTask(showCompleted)


    suspend fun toggleCompleted(completed: Boolean, id: Int) =
        database.taskDao().toggleCompleted(completed, id)

}