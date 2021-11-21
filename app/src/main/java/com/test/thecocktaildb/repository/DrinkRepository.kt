package com.test.thecocktaildb.repository

import com.test.thecocktaildb.api.DrinkApi
import javax.inject.Inject

class DrinkRepository @Inject constructor(private val drinkApi: DrinkApi) {
}