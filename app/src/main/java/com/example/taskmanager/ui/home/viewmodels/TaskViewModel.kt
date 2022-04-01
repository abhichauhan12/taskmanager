package com.example.taskmanager.ui.home.viewmodels


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.repo.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val allTasks = taskRepository.getTasks()

    val showCompleted = taskRepository.showCompleted
    val sortByPriority = taskRepository.sortByPriority
    val sortByDeadline = taskRepository.sortByDeadline

    private val itemToBeDeleted: HashSet<Int> = HashSet()
    private val itemToBeMarkAsCompleted: HashMap<Int, Boolean> = HashMap()

    val tasks = combine(
        showCompleted,
        sortByPriority,
        sortByDeadline,
        allTasks
    ) { showCompleted, showPriority, showDeadline, _ ->
        when {
            showDeadline && showPriority -> taskRepository.getCompletedPriorityDeadlineTask(
                showCompleted = showCompleted
            )
            showDeadline -> taskRepository.getDeadlineSort(showCompleted)
            showPriority -> taskRepository.getPriorityInc(showCompleted)
            else -> taskRepository.getTasks(showCompleted)
        }
    }

    val searchTasks = taskRepository.getTasks().stateIn(viewModelScope, SharingStarted.Lazily, ArrayList())

    fun updateShowCompleted(showCompleted: Boolean) {
        viewModelScope.launch { taskRepository.updateShowCompleted(showCompleted) }
    }

    fun updateShowPriority(showPriority: Boolean) {
        viewModelScope.launch { taskRepository.updateShowPriority(showPriority) }
    }

    fun updateShowDeadline(showDeadline: Boolean) {
        viewModelScope.launch { taskRepository.updateShowDeadline(showDeadline) }
    }

    fun delete() {
        viewModelScope.launch {
            itemToBeDeleted.forEach {
                taskRepository.deleteTask(id = it)
            }
        }
    }

    fun updateItemToBeDeleted(id: Int) {
        if (itemToBeDeleted.contains(id)) itemToBeDeleted.remove(id)
        else itemToBeDeleted.add(id)
    }

    fun updateItemToBeMarkAsCompleted(id: Int, completed: Boolean) {
        if (itemToBeMarkAsCompleted.containsKey(id)) itemToBeMarkAsCompleted.remove(id)
        else itemToBeMarkAsCompleted[id] = !completed
    }

    fun markAsCompleted() {
        viewModelScope.launch {
            itemToBeMarkAsCompleted.forEach { (id, completed) ->
                taskRepository.toggleCompleted(completed, id)
            }.also { itemToBeMarkAsCompleted.clear() }
        }
    }
}