package com.crisangel.recipesapp.data.remote

import com.crisangel.recipesapp.data.model.RecipeResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface RecipesApi {

    @GET("default/crisAngel")
   suspend fun recipesApi(): RecipeResponse
}