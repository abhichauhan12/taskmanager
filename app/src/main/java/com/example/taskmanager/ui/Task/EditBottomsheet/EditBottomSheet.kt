package com.example.taskmanager.ui.Task.EditBottomsheet

import TaskViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.databinding.FragmentEditBottomSheetBinding
import com.example.taskmanager.domain.repo.TaskRepository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditBottomSheet() : BottomSheetDialogFragment(){

    private lateinit var binding : FragmentEditBottomSheetBinding
    private lateinit var taskViewModel: TaskViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditBottomSheetBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
//        return inflater.inflate(R.layout.fragment_edit_bottom_sheet,container,false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task : Task = requireArguments().getParcelable("task")!!
        taskViewModel = ViewModelProvider(
            this,
            TaskViewModel.Factor(taskRepository = TaskRepository.getInstance(requireContext()))
        )[TaskViewModel::class.java]

        binding.completedBottomSheet.text=if (task.completed) "Mark as not completed" else "Mark as completed"


        binding.completedBottomSheet.setOnClickListener {
            taskViewModel.updateItemToBeMarkAsCompleted(id=task.id!!,completed = task.completed)
            taskViewModel.markAsCompleted()
            dismiss()

        }

        binding.deleteBottomsheet.setOnClickListener {
            taskViewModel.updateItemToBeDeleted(id = task.id!!)
            taskViewModel.delete()

            Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT).show()
            dismiss()

        }



    }
}