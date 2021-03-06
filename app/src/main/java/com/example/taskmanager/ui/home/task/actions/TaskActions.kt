package com.example.taskmanager.ui.home.task.actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.data.database.entities.Task
import com.example.taskmanager.databinding.FragmentTaskActionsBinding
import com.example.taskmanager.domain.repo.TaskRepository
import com.example.taskmanager.ui.home.viewmodels.TaskViewModel
import com.example.taskmanager.utils.BundleConstants.TASK
import com.example.taskmanager.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskActions : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTaskActionsBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskActionsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task: Task = requireArguments().getParcelable(TASK)!!

        taskViewModel = TaskViewModel.get(this, requireContext())

        binding.completedBottomSheet.text = if (task.completed) "Mark as not completed" else "Mark as completed"

        binding.completedBottomSheet.setOnClickListener {
            taskViewModel.updateItemToBeMarkAsCompleted(id = task.id!!, completed = task.completed)
            taskViewModel.markAsCompleted().also { dismiss() }
        }

        binding.deleteBottomsheet.setOnClickListener {
            taskViewModel.updateItemToBeDeleted(id = task.id!!)
            taskViewModel.delete()
            showToast("deleted").also { dismiss() }
        }
    }
}