package com.example.taskmanager.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.repo.AppRepository
import com.example.taskmanager.utils.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UtilsViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    val theme = appRepository.appTheme


    private val _selectedColor = MutableSharedFlow<Int>()
    val selectedColor = _selectedColor.asSharedFlow()

    fun updateSelectedColor(color: Int) {
        viewModelScope.launch { _selectedColor.emit(color) }
    }

    fun updateAppTheme(theme: Theme) {
        viewModelScope.launch { appRepository.updateAppTheme(theme) }
    }
}