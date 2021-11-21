package com.test.thecocktaildb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.test.thecocktaildb.api.DrinkApi
import com.test.thecocktaildb.data.DrinkPageSource
import javax.inject.Inject

class DrinkRepository @Inject constructor(private val drinkApi: DrinkApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DrinkPageSource(drinkApi, query) }
        ).liveData

}