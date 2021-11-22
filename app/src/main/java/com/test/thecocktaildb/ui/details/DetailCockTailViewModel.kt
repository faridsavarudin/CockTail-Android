package com.test.thecocktaildb.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.thecocktaildb.DrinksListResponse
import com.test.thecocktaildb.repository.DrinkRepository
import com.test.thecocktaildb.utils.Resource
import kotlinx.coroutines.launch

class DetailCockTailViewModel  @ViewModelInject constructor(
        private val repository: DrinkRepository
) : ViewModel() {

    private lateinit var detail: LiveData<Resource<DrinksListResponse>>

    init {
        initDetail("")
    }
    fun initDetail(id: String) = viewModelScope.launch {
        detail = repository.detailCockTail(id)
    }

    fun getDetail() = detail
}