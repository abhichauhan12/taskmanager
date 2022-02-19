package com.example.taskmanager.ui.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.databinding.FragmentAddTaskBinding
import com.example.taskmanager.domain.repo.TaskRepository
import com.example.taskmanager.utils.TaskConstants

class AddTask : Fragment(R.layout.fragment_add_task) {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var addTaskViewModel: AddTaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTaskBinding.bind(view)
        binding.lifecycleOwner = this

        addTaskViewModel = ViewModelProvider(
            this,
            AddTaskViewModel.Factor(taskRepository = TaskRepository.getInstance(requireContext()))
        )[AddTaskViewModel::class.java]

        binding.saveButton.setOnClickListener {
            saveDataAndGoback()
        }

        binding.backButton.setOnClickListener {
            Toast.makeText(requireContext(), "backbutton press", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTask_to_task)
        }


    }

    private fun saveDataAndGoback() {
        val tasktext = binding.task.text.toString()
        if (tasktext.isNotBlank()) {
            val task = Task(
                id = TaskConstants.randomInt,
                title = binding.title.text.toString(),
                task = binding.task.text.toString(),
                priority = TaskConstants.PRIORITY_DEFAULT,
                taskColor = TaskConstants.COLOR_DEFAULT,
                time = TaskConstants.TIME_DEFAULT,
                deadline = TaskConstants.DEADLINE_DEFAULT,
                completed = false,
            )
            addTaskViewModel.insertTaskInDatabase(task)
            findNavController().navigate(R.id.action_addTask_to_task)


        }

    }


}