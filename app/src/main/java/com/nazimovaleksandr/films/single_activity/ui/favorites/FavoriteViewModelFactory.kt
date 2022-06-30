package com.nazimovaleksandr.films.single_activity.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nazimovaleksandr.films.single_activity.data.DataManager

class FavoriteViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(dataManager) as T
        }

        throw IllegalArgumentException("ViewModel not found")
    }
}