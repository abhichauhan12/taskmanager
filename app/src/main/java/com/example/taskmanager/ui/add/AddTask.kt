package com.example.taskmanager.ui.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.databinding.FragmentAddTaskBinding
import com.example.taskmanager.domain.repo.TaskRepository
import com.example.taskmanager.ui.HomeActivity
import com.example.taskmanager.ui.UtilsViewModel
import com.example.taskmanager.utils.TaskConstants
import com.example.taskmanager.utils.TaskConstants.COLOR_DEFAULT
import com.example.taskmanager.utils.TaskConstants.INVALID_COLOR
import com.example.taskmanager.utils.getFormattedTime
import com.example.taskmanager.utils.getParsedColor
import kotlinx.coroutines.flow.collect

class AddTask : Fragment(R.layout.fragment_add_task) {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var addTaskViewModel: AddTaskViewModel

    private var color = COLOR_DEFAULT

    private val utilsViewModel by lazy {
        ViewModelProvider(requireActivity() as HomeActivity,UtilsViewModel.Factory())[UtilsViewModel::class.java]
    }

    private var onSaveButtonClicked : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTaskBinding.bind(view)
        binding.lifecycleOwner = this

        addTaskViewModel = ViewModelProvider(
            this,
            AddTaskViewModel.Factor(taskRepository = TaskRepository.getInstance(requireContext()))
        )[AddTaskViewModel::class.java]

        val task: Task? = arguments?.getParcelable("task")
        if (task != null) {
            binding.title.setText(task.title)
            binding.task.setText(task.task)
            binding.time.setText(getFormattedTime(if (task.time.isNotBlank()) task.time.toLong() else 0))
            binding.priority.value = task.priority.toFloat()
            binding.priorityText.text=getString(R.string.priority,task.priority)
            binding.addTaskContainer.setBackgroundColor(task.taskColor)
            color = task.taskColor
            (activity as HomeActivity).window.statusBarColor = task.taskColor

        } else {
            binding.time.setText(getFormattedTime(System.currentTimeMillis()))
            binding.priorityText.text=getString(R.string.priority,3)

        }

        binding.saveButton.setOnClickListener {
            onSaveButtonClicked=true
            saveDataAndGoback()
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()

        }

        binding.colorPalette.setOnClickListener {
            findNavController().navigate(R.id.action_addTask_to_colorBottomSheet)
        }

        binding.priority.addOnChangeListener { _, value, _ ->
            binding.priorityText.text=getString(R.string.priority,value.toInt())
        }

        lifecycleScope.launchWhenStarted{
            utilsViewModel.selectedColor.collect {
                color=it
                binding.addTaskContainer.setBackgroundColor(color)
                (activity as HomeActivity).window.statusBarColor = color

            }

        }


    }

    private fun saveDataAndGoback() {
        val taskTitle = binding.title.text.toString()
        val taskSubtitle = binding.task.text.toString()
        val task: Task? = arguments?.getParcelable("task")


        if (taskTitle.isNotBlank() && taskSubtitle.isNotBlank()) {
            if (task == null) {
                val newTask = Task(
                    title = binding.title.text.toString(),
                    task = binding.task.text.toString(),
                    priority = binding.priority.value.toInt(),
                    taskColor = color,
                    time = System.currentTimeMillis().toString(),
                    deadline = TaskConstants.DEADLINE_DEFAULT,
                    completed = false
                )

                addTaskViewModel.insertTaskInDatabase(newTask)
                findNavController().navigateUp()

            } else {
                val updatedTask = Task(
                    id = task.id,
                    title = binding.title.text.toString(),
                    task = binding.task.text.toString(),
                    priority = binding.priority.value.toInt(),
                    taskColor =color ,
                    time = task.time,
                    deadline = task.deadline,
                    completed = task.completed
                )
                addTaskViewModel.updateTask(updatedTask)
                findNavController().navigateUp()
            }
        } else {
            Toast.makeText(requireContext(), "Title and Task can not be empty ", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStop() {
        super.onStop()
        if (!onSaveButtonClicked){
            saveDataAndGoback()
        }

    }
}