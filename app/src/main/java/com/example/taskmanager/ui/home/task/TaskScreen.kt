package com.example.taskmanager.ui.home.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskScreenBinding
import com.example.taskmanager.ui.home.task.adapters.TaskAdapter
import com.example.taskmanager.ui.home.viewmodels.TaskViewModel
import com.example.taskmanager.utils.BundleConstants.TASK
import com.example.taskmanager.utils.TaskConstants.COLOR_DEFAULT
import com.example.taskmanager.utils.safeNavigate
import com.example.taskmanager.utils.statusBarColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskScreen : Fragment(R.layout.fragment_task_screen) {

    private lateinit var binding: FragmentTaskScreenBinding
    private lateinit var taskViewModel: TaskViewModel

    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter(
            onItemClick = { safeNavigate(R.id.action_task_to_add_task, Bundle().apply { putParcelable(TASK, it) }) },

            onMenuClicked = { safeNavigate(R.id.action_task_to_task_actions, Bundle().apply { putParcelable(TASK, it) }) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaskScreenBinding.bind(view)
        binding.lifecycleOwner = this

        initToolbar()

        statusBarColor(COLOR_DEFAULT)

        taskViewModel = TaskViewModel.get(this, requireContext())

        val linearLayoutManager= LinearLayoutManager(requireContext())

        val dividerItemDecoration = DividerItemDecoration(requireContext(), linearLayoutManager.orientation)

        binding.taskRecyclerView.apply {
            adapter = taskAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
        }

        binding.addTask.setOnClickListener { safeNavigate(R.id.action_task_to_add_task) }

        binding.menuTaskFragment.setOnClickListener { safeNavigate(R.id.action_task_to_task_menu) }

        lifecycleScope.launch {
            taskViewModel.tasks.collect {
                taskAdapter.submitList(it)
                binding.emptyListText.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        }

    }

    private fun initToolbar() {
        binding.toolBarTaskFragment.titleToolbar.text= "Tasks"
    }
}
