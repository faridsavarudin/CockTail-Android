package com.test.thecocktaildb

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrinksListResponse(
	val drinks: List<DrinksItem?>? = null
) : Parcelable

@Parcelize
data class DrinksItem(

	val strDrink: String?,

	val strDrinkThumb: String?,

	val idDrink: String?
) : Parcelable