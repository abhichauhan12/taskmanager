package com.example.taskmanager.ui.Task

import TaskViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.databinding.FragmentMenuBottomSheetBinding
import com.example.taskmanager.domain.repo.TaskRepository
import com.example.taskmanager.ui.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect


class MenuBottomSheet() : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var taskViewModel: TaskViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewModel = ViewModelProvider(
            this,
            TaskViewModel.Factor(taskRepository = TaskRepository.getInstance(requireContext()))
        )[TaskViewModel::class.java]

        lifecycleScope.launchWhenStarted {
            taskViewModel.showCompleted.collect {
                binding.completeTask.isChecked = it
            }
        }

        lifecycleScope.launchWhenStarted {
            taskViewModel.sortByPriority.collect {
                binding.priorityTask.isChecked = it
            }
        }

        lifecycleScope.launchWhenStarted {
            taskViewModel.sortByDeadline.collect {
                binding.deadlineTask.isChecked = it
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