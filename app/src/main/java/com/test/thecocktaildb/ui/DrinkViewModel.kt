package com.test.thecocktaildb.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.test.thecocktaildb.DrinksListResponse
import com.test.thecocktaildb.repository.DrinkRepository
import com.test.thecocktaildb.utils.Resource
import kotlinx.coroutines.launch

class DrinkViewModel @ViewModelInject constructor(
    private val repository: DrinkRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val currentQueryAlcoholic = MutableLiveData("Alcoholic")
    private val currentQueryGlass = MutableLiveData("Cocktail_glass")
    private val currentQueryCategory = MutableLiveData("Ordinary_Drink")


    val queryAlcoholic = currentQueryAlcoholic.switchMap { query ->
        repository.filterByAlcoholic(query).cachedIn(viewModelScope)
    }

    val queryGlass = currentQueryGlass.switchMap { query ->
        repository.filterByGlass(query).cachedIn(viewModelScope)
    }

    val queryCategory = currentQueryCategory.switchMap { query ->
        repository.filterByCategory(query).cachedIn(viewModelScope)
    }

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
