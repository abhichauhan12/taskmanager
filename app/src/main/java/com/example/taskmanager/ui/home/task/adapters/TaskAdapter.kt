package com.example.taskmanager.ui.home.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.data.database.entities.Task
import com.example.taskmanager.databinding.ItemTaskBinding

class TaskAdapter(
    private val onItemClick :(Task) -> Unit,
    private val onMenuClicked:(Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskItems = task
            binding.executePendingBindings()
        }

        init {
            binding.itemContainer.setOnClickListener {
                val task: Task = binding.taskItems!!
                onItemClick(task)
            }

            binding.menuItem.setOnClickListener {
                val task: Task = binding.taskItems!!
                onMenuClicked(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(task = getItem(position))
    }
}
