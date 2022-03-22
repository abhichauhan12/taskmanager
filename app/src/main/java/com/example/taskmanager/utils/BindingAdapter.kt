package com.example.taskmanager.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.taskmanager.utils.TaskConstants.DEFAULT_DEADLINE

@BindingAdapter("markDown")
fun applyMarkDownTextToTextView(textView: TextView, string : String?) {
    string?.let { textView.text = it.getMarkDownText(textView.context) }
}

@BindingAdapter("deadlineVisibility")
fun hideDeadlineIfDefaultTime(textView: TextView, time : Long?) {
    textView.visibility = if (time == DEFAULT_DEADLINE) View.GONE else View.VISIBLE
}