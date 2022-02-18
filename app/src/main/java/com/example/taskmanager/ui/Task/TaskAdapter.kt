package com.example.taskmanager.ui.Task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.data.entities.TaskEntitiy
import com.example.taskmanager.databinding.ItemTaskBinding


class TaskAdapter(
    private val inflater: LayoutInflater

) : ListAdapter<TaskEntitiy,TaskAdapter.TaskViewHolder >(DiffUtilCallback){

    object DiffUtilCallback : DiffUtil.ItemCallback<TaskEntitiy>() {
        override fun areItemsTheSame(oldItem: TaskEntitiy, newItem: TaskEntitiy): Boolean {
            return oldItem.task == newItem.task
        }

        override fun areContentsTheSame(oldItem: TaskEntitiy, newItem: TaskEntitiy): Boolean {
            return oldItem == newItem
        }

    }

    class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task : TaskEntitiy) {
            binding.taskItem = task
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task =task)
    }

}
