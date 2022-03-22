package com.example.taskmanager.ui.home.task

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskScreenBinding
import com.example.taskmanager.domain.repo.AppRepository
import com.example.taskmanager.ui.home.task.adapters.TaskAdapter
import com.example.taskmanager.ui.home.viewmodels.TaskViewModel
import com.example.taskmanager.utils.BundleConstants.TASK
import com.example.taskmanager.utils.Theme
import com.example.taskmanager.utils.safeNavigate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskScreen : Fragment(R.layout.fragment_task_screen) {

    private lateinit var binding: FragmentTaskScreenBinding
    private lateinit var taskViewModel: TaskViewModel
    private val appRepository by lazy { AppRepository.getInstance(requireContext()) }

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

//        statusBarColor(COLOR_DEFAULT)

        taskViewModel = TaskViewModel.get(this, requireContext())

        binding.taskRecyclerView.apply {
            adapter = taskAdapter
        }

        binding.addTask.setOnClickListener { safeNavigate(R.id.action_task_to_add_task) }

        binding.menuTaskFragment.setOnClickListener { safeNavigate(R.id.action_task_to_task_menu) }

        lifecycleScope.launch {
            taskViewModel.tasks.collect {
                taskAdapter.submitList(it)
                binding.emptyListText.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        }

        binding.toolBarTaskFragment.searchToolbar.setOnClickListener {
            safeNavigate(R.id.action_task_to_search)
        }

        binding.toolBarTaskFragment.themeToolbar.setOnClickListener {
            lifecycleScope.launchWhenStarted { checkAndUpdateAppTheme() }
        }

    }

    private suspend fun checkAndUpdateAppTheme() {
        val theme = appRepository.appTheme.value ?: return

        when(Theme.valueOf(theme)) {
            Theme.SYSTEM -> appRepository.updateAppTheme(Theme.DARK)

            Theme.DARK ->  appRepository.updateAppTheme(Theme.LIGHT)

            Theme.LIGHT -> appRepository.updateAppTheme(Theme.DARK)
        }
    }

    private fun initToolbar() {
        binding.toolBarTaskFragment.titleToolbar.text= "Tasks"
        binding.toolBarTaskFragment.themeToolbar.apply {

        if(appRepository.appTheme.value == Theme.DARK.name){
                setImageResource(R.drawable.ic_dark_mode)
            }else{
                setImageResource(R.drawable.ic_light_mode)
            }
        }
    }
}
