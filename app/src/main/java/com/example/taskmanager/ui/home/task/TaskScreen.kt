package com.example.taskmanager.ui.home.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskScreenBinding
import com.example.taskmanager.ui.home.task.adapters.TaskAdapter
import com.example.taskmanager.ui.home.viewmodels.TaskViewModel
import com.example.taskmanager.ui.home.viewmodels.UtilsViewModel
import com.example.taskmanager.utils.BundleConstants.TASK
import com.example.taskmanager.utils.Theme
import com.example.taskmanager.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskScreen : Fragment(R.layout.fragment_task_screen) {

    private lateinit var binding: FragmentTaskScreenBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private val utilsViewModel: UtilsViewModel by viewModels()

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

        binding.taskRecyclerView.apply {
            adapter = taskAdapter
        }

        binding.addTask.setOnClickListener { safeNavigate(R.id.action_task_to_add_task) }

        binding.bottomAppBar.setNavigationOnClickListener { safeNavigate(R.id.action_task_to_task_menu) }

        binding.bottomAppBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.theme) checkAndUpdateAppTheme()

            true
        }

        lifecycleScope.launch {
            taskViewModel.tasks.collect {
                taskAdapter.submitList(it)
                binding.emptyListText.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        }

    }

    private fun checkAndUpdateAppTheme() {
        val theme = utilsViewModel.theme.value ?: return

        when(Theme.valueOf(theme)) {
            Theme.SYSTEM -> utilsViewModel.updateAppTheme(Theme.DARK)

            Theme.DARK ->  utilsViewModel.updateAppTheme(Theme.LIGHT)

            Theme.LIGHT -> utilsViewModel.updateAppTheme(Theme.DARK)
        }
    }

    private fun initToolbar() {

        binding.toolBarTaskFragment.container.setOnClickListener {
            safeNavigate(R.id.action_task_to_search)
        }

        if (utilsViewModel.theme.value == Theme.DARK.name)
            binding.bottomAppBar.replaceMenu(R.menu.task_screen_menu_night)
        else
            binding.bottomAppBar.replaceMenu(R.menu.task_screen_menu_light)
    }
}
