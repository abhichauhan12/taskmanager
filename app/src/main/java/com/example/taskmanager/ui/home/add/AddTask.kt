package com.example.taskmanager.ui.home.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.R
import com.example.taskmanager.data.database.entities.Task
import com.example.taskmanager.databinding.FragmentAddTaskBinding
import com.example.taskmanager.ui.home.viewmodels.AddTaskViewModel
import com.example.taskmanager.ui.home.viewmodels.UtilsViewModel
import com.example.taskmanager.utils.*
import com.example.taskmanager.utils.BundleConstants.TASK
import com.example.taskmanager.utils.TaskConstants.COLOR_DEFAULT
import com.example.taskmanager.utils.TaskConstants.DEFAULT_DEADLINE
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddTask : Fragment(R.layout.fragment_add_task) {

    private lateinit var binding: FragmentAddTaskBinding

    private val addTaskViewModel by viewModels<AddTaskViewModel>()
    private val utilsViewModel by activityViewModels<UtilsViewModel>()

    private var onSaveButtonClicked: Boolean = false
    private var color = COLOR_DEFAULT
    private var deadline: Long = DEFAULT_DEADLINE

    private val deadlinePicker by lazy {
        val selectedDateShouldBe =
            if (deadline == DEFAULT_DEADLINE) System.currentTimeMillis() else deadline

        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select deadline").setSelection(selectedDateShouldBe).build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddTaskBinding.bind(view)
        binding.lifecycleOwner = this

        val task: Task? = arguments?.getParcelable(TASK)

        if (task != null) {
            binding.title.setText(task.title)
            binding.task.setText(task.task)

            binding.time.text =
                getFormattedTime(if (task.time.isNotBlank()) task.time.toLong() else 0)
            binding.priority.value = task.priority.toFloat()
            binding.priorityText.text = getString(R.string.priority, task.priority)
            binding.addTaskContainer.setBackgroundColor(task.taskColor)
            color = task.taskColor
            deadline = task.deadline

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

        binding.deadline.setOnClickListener { openDatePicker() }

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

    private fun openDatePicker() {
        deadlinePicker.addOnPositiveButtonClickListener { deadline = it }
        deadlinePicker.showNow(childFragmentManager, null)
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
                    deadline = deadline,
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
                    deadline = deadline,
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