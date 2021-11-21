package com.test.thecocktaildb.data

import android.util.Log
import androidx.paging.PagingSource
import com.test.thecocktaildb.DrinksItem
import com.test.thecocktaildb.api.DrinkApi
import retrofit2.HttpException
import java.io.IOException

private const val PAGE = 1

class CategoryPageSource(
    private val drinkApi: DrinkApi,
    private val query: String
) : PagingSource<Int, DrinksItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DrinksItem> {
        val position = params.key ?: PAGE

        return try {
            val response = drinkApi.filterByCategory(query, position, params.loadSize)
            val photos = response.drinks

            LoadResult.Page(
                data = photos,
                prevKey = if (position == PAGE) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}