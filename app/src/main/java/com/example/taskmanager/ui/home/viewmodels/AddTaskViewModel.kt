package com.example.taskmanager.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.database.entities.Task
import com.example.taskmanager.domain.repo.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    fun insertTaskInDatabase(task: Task) { viewModelScope.launch { taskRepository.insertTask(task) } }
    fun updateTask(task: Task) { viewModelScope.launch { taskRepository.updateTask(task) } }

}