package com.example.taskmanager.ui.home.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.R
import com.example.taskmanager.data.database.entities.Task
import com.example.taskmanager.databinding.FragmentAddTaskBinding
import com.example.taskmanager.ui.home.HomeActivity
import com.example.taskmanager.ui.home.viewmodels.AddTaskViewModel
import com.example.taskmanager.ui.home.viewmodels.UtilsViewModel
import com.example.taskmanager.utils.*
import com.example.taskmanager.utils.BundleConstants.TASK
import com.example.taskmanager.utils.TaskConstants.COLOR_DEFAULT
import kotlinx.coroutines.flow.collect

class AddTask : Fragment(R.layout.fragment_add_task) {

    private lateinit var binding: FragmentAddTaskBinding

    private lateinit var addTaskViewModel: AddTaskViewModel
    private lateinit var utilsViewModel: UtilsViewModel

    private var onSaveButtonClicked: Boolean = false
    private var color = COLOR_DEFAULT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddTaskBinding.bind(view)
        binding.lifecycleOwner = this

        addTaskViewModel = AddTaskViewModel.get(owner = this, context = requireContext())

        utilsViewModel = UtilsViewModel.get(requireActivity() as HomeActivity)

        val task: Task? = arguments?.getParcelable(TASK)

        if (task != null) {
            binding.title.setText(task.title)
            binding.task.setText(task.task)

            binding.time.text = getFormattedTime(if (task.time.isNotBlank()) task.time.toLong() else 0)
            binding.priority.value = task.priority.toFloat()
            binding.priorityText.text = getString(R.string.priority, task.priority)
            binding.addTaskContainer.setBackgroundColor(task.taskColor)
            color = task.taskColor

            statusBarColor(color = task.taskColor)
        } else {
            binding.time.text = getFormattedTime(System.currentTimeMillis())
            binding.priorityText.text = getString(R.string.priority, 3)
        }

        binding.saveButton.setOnClickListener {
            onSaveButtonClicked = true
            saveDataAndGoBack()
        }

        binding.backButton.setOnClickListener { navigateUp() }

        binding.colorPalette.setOnClickListener { safeNavigate(R.id.action_addTask_to_colorBottomSheet) }

        binding.priority.addOnChangeListener { _, value, _ ->
            binding.priorityText.text = getString(R.string.priority, value.toInt())
        }

        lifecycleScope.launchWhenStarted {
            utilsViewModel.selectedColor.collect {
                color = it
                binding.addTaskContainer.setBackgroundColor(color)
                statusBarColor(color)
            }
        }

    }

    private fun saveDataAndGoBack() {
        val taskTitle = binding.title.text.toString()
        val taskSubtitle = binding.task.text.toString()
        val task: Task? = arguments?.getParcelable(TASK)

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

                addTaskViewModel.insertTaskInDatabase(newTask).also { navigateUp() }
            } else {
                val updatedTask = Task(
                    id = task.id,
                    title = binding.title.text.toString(),
                    task = binding.task.text.toString(),
                    priority = binding.priority.value.toInt(),
                    taskColor = color,
                    time = task.time,
                    deadline = task.deadline,
                    completed = task.completed
                )

                addTaskViewModel.updateTask(updatedTask).also { navigateUp() }
            }
        } else {
            showToast("Title and Task can not be empty")
        }
    }

    override fun onStop() {
        super.onStop()
        if (!onSaveButtonClicked) saveDataAndGoBack()
    }
}