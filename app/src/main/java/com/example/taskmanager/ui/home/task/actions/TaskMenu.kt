package com.example.taskmanager.ui.home.task.actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.databinding.FragmentTaskMenuBinding
import com.example.taskmanager.domain.repo.TaskRepository
import com.example.taskmanager.ui.home.viewmodels.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskMenu : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTaskMenuBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskMenuBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewModel = TaskViewModel.get(this, requireContext())

        lifecycleScope.launchWhenStarted {

            launch {
                taskViewModel.showCompleted.collect {
                    binding.completeTask.isChecked = it
                }
            }

            launch {
                taskViewModel.sortByPriority.collect {
                    binding.priorityTask.isChecked = it
                }
            }

            launch {
                taskViewModel.sortByDeadline.collect {
                    binding.deadlineTask.isChecked = it
                }
            }
        }

        binding.completeTask.setOnCheckedChangeListener { _, isChecked ->
            taskViewModel.updateShowCompleted(showCompleted = isChecked)
        }

        binding.priorityTask.setOnCheckedChangeListener { _, isChecked ->
            taskViewModel.updateShowPriority(showPriority = isChecked)
        }

        binding.deadlineTask.setOnCheckedChangeListener { _, isChecked ->
            taskViewModel.updateShowDeadline(showDeadline = isChecked)
        }
    }

}