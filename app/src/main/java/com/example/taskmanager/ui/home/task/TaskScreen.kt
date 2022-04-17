package com.example.taskmanager.ui.home.task

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskScreenBinding
import com.example.taskmanager.domain.model.AuthStatus
import com.example.taskmanager.domain.model.SignOutStatus
import com.example.taskmanager.ui.home.task.adapters.TaskAdapter
import com.example.taskmanager.ui.home.viewmodels.AuthViewModel
import com.example.taskmanager.ui.home.viewmodels.TaskViewModel
import com.example.taskmanager.ui.home.viewmodels.UtilsViewModel
import com.example.taskmanager.utils.*
import com.example.taskmanager.utils.BundleConstants.TASK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskScreen : Fragment(R.layout.fragment_task_screen) {

    private lateinit var binding: FragmentTaskScreenBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private val utilsViewModel: UtilsViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var loader : AlertDialog

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

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    taskViewModel.tasks.collect {
                        taskAdapter.submitList(it)
                        binding.emptyListText.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    authViewModel.authStatus.collect {
                        it?.let {
                            when(it) {
                                AuthStatus.CHECKING_SESSION -> {
                                    loader = loader("Syncing...")
                                    loader.show()
                                }
                                AuthStatus.USER_SIGNED_IN -> showToast("already signed in").also { loader.dismiss() }
                                AuthStatus.USER_NOT_SIGNED_IN -> {
                                    loader.dismiss()
                                    safeNavigate(R.id.action_task_to_login)
                                    showToast("Kindly sign in to continue")
                                }
                            }

                            authViewModel.resetAuthStatus()
                        }
                    }
                }

                launch {
                    authViewModel.signOutStatus.collect {
                        it?.let {
                            when(it) {
                                SignOutStatus.SIGNING_OUT -> {
                                    loader = loader("Signing out...")
                                    loader.show()
                                }
                                SignOutStatus.SIGNED_OUT -> showToast("Signed out successfully").also { loader.dismiss() }
                                SignOutStatus.SIGN_OUT_FAILED -> showToast("Signed out failed").also { loader.dismiss() }
                            }

                            authViewModel.resetSignOutStatus()
                        }
                    }
                }
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
            else if (it.itemId == R.id.sign_out) signOutUser()

            true
        }

        binding.toolBarTaskFragment.syncButton.setOnClickListener {
            authViewModel.isUserSignedIn()
        }
    }

    private fun signOutUser() { authViewModel.signOut() }

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
