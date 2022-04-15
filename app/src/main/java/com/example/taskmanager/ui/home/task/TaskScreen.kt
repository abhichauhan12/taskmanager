package com.example.taskmanager.ui.home.task

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskScreenBinding
import com.example.taskmanager.ui.home.task.adapters.TaskAdapter
import com.example.taskmanager.ui.home.viewmodels.AuthViewModel
import com.example.taskmanager.ui.home.viewmodels.TaskViewModel
import com.example.taskmanager.ui.home.viewmodels.UtilsViewModel
import com.example.taskmanager.utils.BundleConstants.TASK
import com.example.taskmanager.utils.Theme
import com.example.taskmanager.utils.safeNavigate
import com.example.taskmanager.utils.showToast
import com.example.taskmanager.utils.statusBarColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskScreen : Fragment(R.layout.fragment_task_screen) {

    private lateinit var binding: FragmentTaskScreenBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private val utilsViewModel: UtilsViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()

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
        initViews()
        attachObservers()
        toggleStatusBarColor()
    }

    private fun attachObservers() {
        lifecycleScope.launch {
            taskViewModel.tasks.collect {
                taskAdapter.submitList(it)
                binding.emptyListText.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initViews() {
        binding.taskRecyclerView.apply {
            adapter = taskAdapter
        }

        binding.addTask.setOnClickListener { safeNavigate(R.id.action_task_to_add_task) }

        binding.bottomAppBar.setNavigationOnClickListener { safeNavigate(R.id.action_task_to_task_menu) }

        binding.bottomAppBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.theme) checkAndUpdateAppTheme()

            true
        }

        binding.toolBarTaskFragment.syncButton.setOnClickListener {
            checkUserExistAndSync()
        }
    }

    private fun checkUserExistAndSync() {
        // Check if user exists
        val isUserLoggedIn = authViewModel.isUserLoggedIn()
        if (isUserLoggedIn) {
            // if exists , then sync // later on

        }else{
            // else, take him to the auth page

        }

        showToast("$isUserLoggedIn")
    }

    private fun toggleStatusBarColor() {
        val theme = utilsViewModel.theme.value ?: return

        val color = when(Theme.valueOf(theme)) {
            Theme.DARK,
            Theme.SYSTEM -> ResourcesCompat.getColor(resources, R.color.blue, null)

            Theme.LIGHT -> ResourcesCompat.getColor(resources, R.color.white, null)
        }

        statusBarColor(color)
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
