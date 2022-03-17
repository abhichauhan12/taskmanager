package com.example.taskmanager.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.domain.repo.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(private val taskRepository: TaskRepository): ViewModel() {


    fun insertTaskInDatabase(task: Task){
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    /*fun deleteTask(task: Task){
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }*/



    fun updateTask(task: Task){
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    class Factor(private val taskRepository: TaskRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddTaskViewModel(taskRepository) as T
        }
    }

}