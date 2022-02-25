package com.example.taskmanager.ui.Task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.databinding.ItemTaskBinding


class TaskAdapter(
    private val inflater: LayoutInflater,
    private val onItemClick :(Task) -> Unit,
    private val onItemSelected:(Int) -> Unit

) : ListAdapter<Task,TaskAdapter.TaskViewHolder >(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    inner class TaskViewHolder(
        val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.taskItems = task
            binding.executePendingBindings()

        }

        init {
            binding.itemContainer.setOnClickListener {
                val task: Task = binding.taskItems!!
                onItemClick(task)
            }
            binding.checkbox.setOnCheckedChangeListener { compoundButton, b ->
                onItemSelected(binding.taskItems!!.id!!)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task = task)




    }
}
