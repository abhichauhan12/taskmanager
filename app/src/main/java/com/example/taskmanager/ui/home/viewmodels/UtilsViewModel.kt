package com.example.taskmanager.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.ui.home.HomeActivity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UtilsViewModel : ViewModel() {

    companion object {
        fun get(activity: HomeActivity) =
            ViewModelProvider(activity, Factory())[UtilsViewModel::class.java]
    }

    private val _selectedColor = MutableSharedFlow<Int>()
    val selectedColor = _selectedColor.asSharedFlow()

    fun updateSelectedColor(color: Int) {
        viewModelScope.launch { _selectedColor.emit(color) }
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UtilsViewModel() as T
        }
    }
}