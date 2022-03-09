package com.example.taskmanager.ui.Task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.utils.getFormattedTime


class TaskAdapter(
    private val inflater: LayoutInflater,
    private val onItemClick :(Task) -> Unit,
    private val onMenuClicked:(Task) -> Unit,


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
//            binding.checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
//                if (!binding.taskItems!!.completed)
//                onItemSelected(binding.taskItems!!.id!!,binding.taskItems!!.completed!!)
//            }

            binding.menuItem.setOnClickListener {
                val task: Task = binding.taskItems!!
                onMenuClicked(task)
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
