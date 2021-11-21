package com.test.thecocktaildb.repository

import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.test.thecocktaildb.api.DrinkApi
import com.test.thecocktaildb.data.AlcoholicPageSource
import com.test.thecocktaildb.data.CategoryPageSource
import com.test.thecocktaildb.data.DrinkPageSource
import com.test.thecocktaildb.data.GlassPageSource
import com.test.thecocktaildb.utils.EspressoIdlingResource
import com.test.thecocktaildb.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DrinkRepository @Inject constructor(private val drinkApi: DrinkApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DrinkPageSource(drinkApi, query) }
        ).liveData

    fun filterByAlcoholic(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlcoholicPageSource(drinkApi, query) }
        ).liveData

    fun filterByCategory(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CategoryPageSource(drinkApi, query) }
        ).liveData

    fun filterByGlass(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GlassPageSource(drinkApi, query) }
        ).liveData

}