package com.crisangel.recipesapp.domain.repositories

import com.crisangel.recipesapp.domain.model.RecipeBean
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

   suspend fun recipesApp(): Flow<RecipeBean>


}