package com.andy_dev.arpractical.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FactsViewModelFactory constructor(private val factsViewModel: FactsViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FactsViewModel::class.java)) {
            return factsViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}