package com.test.thecocktaildb.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.test.thecocktaildb.repository.DrinkRepository

class DrinkViewModel @ViewModelInject constructor(
    private val repository: DrinkRepository
) : ViewModel() {
}
