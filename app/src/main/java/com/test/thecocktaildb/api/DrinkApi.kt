package com.test.thecocktaildb.api

import com.test.thecocktaildb.DrinksListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkApi {

    companion object {
        const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    }

    @GET("search.php")
    suspend fun searchCockTail(
        @Query("s") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): DrinksListResponse

    @GET("filter.php")
    suspend fun filterByAlcohol(
        @Query("a") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): DrinksListResponse

    @GET("filter.php")
    suspend fun filterByGlass(
        @Query("g") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): DrinksListResponse

    @GET("filter.php")
    suspend fun filterByCategory(
        @Query("c") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): DrinksListResponse

    @GET("lookup.php")
    suspend fun detailCockTail(
        @Query("i") query: String
    ): DrinksListResponse

}


