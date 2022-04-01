@file:Suppress("UNCHECKED_CAST", "NestedLambdaShadowedImplicitParameter")

package com.example.taskmanager.ui.home.search

import android.os.Bundle
import android.view.View
import android.widget.Filter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.R
import com.example.taskmanager.data.database.entities.Task
import com.example.taskmanager.databinding.FragmentSearchBinding
import com.example.taskmanager.ui.home.task.adapters.TaskAdapter
import com.example.taskmanager.ui.home.viewmodels.TaskViewModel
import com.example.taskmanager.utils.BundleConstants
import com.example.taskmanager.utils.navigateUp
import com.example.taskmanager.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Search : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val taskViewModel by viewModels<TaskViewModel>()

    private val searchFilter: Filter by lazy {
        object : Filter() {
            override fun performFiltering(query: CharSequence): FilterResults {

                val filteredList = ArrayList<Task>()

                query.toString().trim().lowercase().let { string ->
                    taskViewModel.searchTasks.value.forEach { task ->
                        if (task.title.lowercase().contains(string) ||
                            task.task.lowercase().contains(string)
                        ) {
                            filteredList.add(task)
                        }
                    }
                }

                return FilterResults().apply { values = filteredList }

            }

            override fun publishResults(query: CharSequence, list: FilterResults) {
                val filteredList = list.values as List<Task>
                taskAdapter.submitList(filteredList)
            }

        }
    }

    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter(
            onItemClick = {
                safeNavigate(R.id.action_search_to_addTask, Bundle().apply {
                    putParcelable(BundleConstants.TASK, it)
                })
            },

            onMenuClicked = {
                safeNavigate(R.id.action_search_to_taskActions, Bundle().apply {
                    putParcelable(BundleConstants.TASK, it)
                })
            },

            searchFilter = searchFilter
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        binding.lifecycleOwner = this

        lifecycleScope.launch {
            taskViewModel.searchTasks.collect { taskAdapter.submitList(it) }
        }

        binding.tasksList.apply { adapter = taskAdapter }

        initToolbar()
    }

    private fun initToolbar() {
        binding.upButton.setOnClickListener { navigateUp() }

        binding.searchEdittext.addTextChangedListener { it?.toString()?.let { searchTasks(query = it) } }
    }

    private fun searchTasks(query: String) {
        taskAdapter.filter?.filter(query)
    }

//
//    private fun searchTasks(query : String) {
//        val filteredList = ArrayList<Task>()
//
//        query.trim().lowercase().let { string ->
//
//            taskViewModel.searchTasks.value.forEach { task ->
//                if (task.title.lowercase().contains(string) ||
//                    task.task.lowercase().contains(string)) {
//                    filteredList.add(task)
//                }
//            }
//        }
//
//        taskAdapter.submitList(filteredList)
//
//    }

}