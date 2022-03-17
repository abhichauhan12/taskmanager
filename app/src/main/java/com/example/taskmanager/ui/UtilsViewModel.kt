package com.example.taskmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UtilsViewModel() : ViewModel() {

    private val _selectedColor = MutableSharedFlow<Int>()
    val selectedColor = _selectedColor.asSharedFlow()


    fun updateSelectedColor(color : Int){
        viewModelScope.launch{
            _selectedColor.emit(color)
        }
    }


    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UtilsViewModel() as T
        }
    }

}