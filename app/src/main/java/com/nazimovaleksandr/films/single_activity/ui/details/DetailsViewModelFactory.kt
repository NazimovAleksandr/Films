package com.nazimovaleksandr.films.single_activity.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nazimovaleksandr.films.single_activity.data.DataManager

class DetailsViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(dataManager) as T
        }

        throw IllegalArgumentException("ViewModel not found")
    }
}