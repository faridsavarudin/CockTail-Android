package com.test.thecocktaildb.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.thecocktaildb.repository.DrinkRepository

class DrinkViewModel @ViewModelInject constructor(
    private val repository: DrinkRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val cockTail = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchCockTail(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "margarita"
    }

}
