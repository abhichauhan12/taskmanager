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
import com.example.taskmanager.utils.getFormattedTime

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

        val task :Task? = arguments?.getParcelable("task")
        if (task != null){
            binding.title.setText(task.title)
            binding.task.setText(task.task)
            binding.time.setText(getFormattedTime(if(task.time.isNotBlank()) task.time.toLong() else 0))
            binding.priority.value = task.priority.toFloat()
        }else{
            binding.time.setText(getFormattedTime(System.currentTimeMillis()))
        }

        binding.saveButton.setOnClickListener {
            saveDataAndGoback()
        }

        binding.backButton.setOnClickListener {
            Toast.makeText(requireContext(), "backbutton press", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTask_to_task)
        }
    }

    private fun saveDataAndGoback() {
        val taskTitle = binding.title.text.toString()
        val taskSubtitle = binding.task.text.toString()
        val task :Task? = arguments?.getParcelable("task")




        if (taskTitle.isNotBlank() && taskSubtitle.isNotBlank()){
            if (task == null){
                val newTask = Task(
                    title = binding.title.text.toString(),
                    task = binding.task.text.toString(),
                    priority = binding.priority.value.toInt(),
                    taskColor = TaskConstants.COLOR_DEFAULT,
                    time = System.currentTimeMillis().toString(),
                    deadline = TaskConstants.DEADLINE_DEFAULT,
                    completed = false
                )

                addTaskViewModel.insertTaskInDatabase(newTask)
                findNavController().navigate(R.id.action_addTask_to_task)

            }else{
                val updatedTask = Task(
                    id= task.id,
                    title = binding.title.text.toString(),
                    task = binding.task.text.toString(),
                    priority = binding.priority.value.toInt(),
                    taskColor = task.taskColor,
                    time = task.time,
                    deadline = task.deadline,
                    completed = task.completed
                )
                addTaskViewModel.updateTask(updatedTask)
                findNavController().navigate(R.id.action_addTask_to_task)
            }
        }else{
            Toast.makeText(requireContext(), "Title and Task can not be empty ", Toast.LENGTH_SHORT).show()
        }
    }


}