package com.example.taskmanager.ui.Task

import TaskViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.domain.repo.TaskRepository
import com.example.taskmanager.ui.HomeActivity
import com.example.taskmanager.utils.TaskConstants.COLOR_DEFAULT
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskFragmnet : Fragment(R.layout.fragment_task) {


    private lateinit var binding: FragmentTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter(
            inflater = layoutInflater,
            onItemClick = {
                findNavController().navigate(R.id.action_task_to_addTask, Bundle().apply {
                    putParcelable("task", it)
                })
            },
             onItemSelected = { id ->
                taskViewModel.updateItemToBeDeleted(id)
            }
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaskBinding.bind(view)
        binding.lifecycleOwner = this

        (activity as HomeActivity).window.statusBarColor = COLOR_DEFAULT

        taskViewModel = ViewModelProvider(
            this,
            TaskViewModel.Factor(taskRepository = TaskRepository.getInstance(requireContext()))
        )[TaskViewModel::class.java]

        binding.taskRecyclerView.apply {
            adapter = taskAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
//            layoutManager = LinearLayoutManager(requireContext())

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

        binding.addTask.setOnClickListener {
            findNavController().navigate(R.id.action_task_to_addTask)
        }

        lifecycleScope.launch {
            taskViewModel.tasks.collect {
                    taskAdapter.submitList(it)
                if (it.isNullOrEmpty()){
                    binding.emptyListText.visibility = View.VISIBLE
                }else{
                    binding.emptyListText.visibility = View.INVISIBLE
                }
            }
        }

        binding.deleteTask.setOnClickListener {
            taskViewModel.delete()
        }

    }

}
