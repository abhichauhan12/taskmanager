package com.example.taskmanager.ui.Task

import TaskViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
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
//             onItemSelected = { id ->
//                taskViewModel.updateItemToBeDeleted(id)
//            },

            onMenuClicked = { task ->
                findNavController().navigate(R.id.action_task_to_editBottomSheet, Bundle().apply {
                    putParcelable("task", task)
                })
            }

        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaskBinding.bind(view)
        binding.lifecycleOwner = this

        initToolbar()

        (activity as HomeActivity).window.statusBarColor = COLOR_DEFAULT

        taskViewModel = ViewModelProvider(
            this,
            TaskViewModel.Factor(taskRepository = TaskRepository.getInstance(requireContext()))
        )[TaskViewModel::class.java]

        val linearLayoutManager= LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            linearLayoutManager.orientation
        )

        binding.taskRecyclerView.apply {
            adapter = taskAdapter
//            layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
        }



//        binding.completeTask.setOnCheckedChangeListener { _, isChecked ->
//            taskViewModel.updateShowCompleted(showCompleted = isChecked)
//        }
//
//        binding.priorityTask.setOnCheckedChangeListener { _, isChecked ->
//            taskViewModel.updateShowPriority(showPriority = isChecked)
//        }
//
//        binding.deadlineTask.setOnCheckedChangeListener { _, isChecked ->
//            taskViewModel.updateShowDeadline(showDeadline = isChecked)
//        }


        binding.addTask.setOnClickListener {
            findNavController().navigate(R.id.action_task_to_addTask)
        }

        binding.menuTaskFragment.setOnClickListener {
            findNavController().navigate(R.id.action_task_to_menuBottomSheet)
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

    }

    private fun initToolbar() {
        binding.toolBarTaskFragment.titleToolbar.text="Tasks"
    }

}
