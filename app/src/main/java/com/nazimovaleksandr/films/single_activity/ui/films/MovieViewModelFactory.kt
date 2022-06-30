package com.nazimovaleksandr.films.single_activity.ui.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nazimovaleksandr.films.single_activity.data.DataManager

class MovieViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(dataManager) as T
        }

        throw IllegalArgumentException("ViewModel not found")
    }
}