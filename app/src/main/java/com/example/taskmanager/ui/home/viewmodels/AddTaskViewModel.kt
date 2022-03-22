package com.example.taskmanager.ui.home.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.database.entities.Task
import com.example.taskmanager.domain.repo.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    companion object {
        fun get(owner: ViewModelStoreOwner, context: Context) : AddTaskViewModel {
            return ViewModelProvider(owner,
                Factory(taskRepository = TaskRepository.getInstance(context))
            )[AddTaskViewModel::class.java]
        }
    }

    fun insertTaskInDatabase(task: Task) { viewModelScope.launch { taskRepository.insertTask(task) } }

    fun updateTask(task: Task) { viewModelScope.launch { taskRepository.updateTask(task) } }

    class Factory(private val taskRepository: TaskRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddTaskViewModel(taskRepository) as T
        }
    }
}