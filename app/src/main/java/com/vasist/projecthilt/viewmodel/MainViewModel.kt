package com.vasist.projecthilt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasist.projecthilt.Repository
import com.vasist.projecthilt.model.ApiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (private val repository: Repository): ViewModel() {
    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getQuotes()
        }
    }
    val quotes : LiveData<ApiData>
        get() = repository.quotes
}