package com.example.taskmanager.ui.add

import TaskViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.databinding.FragmentColorBottomSheetBinding
import com.example.taskmanager.databinding.FragmentMenuBottomSheetBinding
import com.example.taskmanager.domain.repo.TaskRepository
import com.example.taskmanager.utils.getParsedColor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ColorBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentColorBottomSheetBinding
    private lateinit var taskViewModel: AddTaskViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorBottomSheetBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewModel = ViewModelProvider(
            this,
            AddTaskViewModel.Factor(taskRepository = TaskRepository.getInstance(requireContext()))
        )[AddTaskViewModel::class.java]

        val task: Task? = arguments?.getParcelable("task")

        var color :String = "#00251a"
        val parsedColor = getParsedColor(color)

        val updatedTask = Task(
            id = task!!.id,
            title = task!!.title,
            task = task!!.task,
            priority = task!!.priority,
            taskColor = parsedColor,
            time = task.time,
            deadline = task.deadline,
            completed = task.completed
        )


        binding.color1.setOnClickListener {
            color = "#00251a"
            taskViewModel.updateTask(updatedTask)
        }

        binding.color2.setOnClickListener {
            color = "#3E2723"
            taskViewModel.updateTask(updatedTask)
        }

        binding.color3.setOnClickListener {
            color = "#01579B"
            taskViewModel.updateTask(updatedTask)
        }

        binding.color4.setOnClickListener {
            color = "#880E4F"
            taskViewModel.updateTask(updatedTask)
        }

        binding.color5.setOnClickListener {
            color = "#B71C1C"
            taskViewModel.updateTask(updatedTask)
        }

        binding.color6.setOnClickListener {
            color = "#212121"
            taskViewModel.updateTask(updatedTask)
        }

        binding.color7.setOnClickListener {
            color = "#000000"
            taskViewModel.updateTask(updatedTask)
        }




    }
}