package com.crisangel.recipesapp.presentation.recipescreen

import com.crisangel.recipesapp.domain.model.ListRecipeBean

sealed class RecipesUiState {
    data object Loading: RecipesUiState()
    data class Success(val listRecipeBean: List<ListRecipeBean>): RecipesUiState()
    data class  Error (val error: String): RecipesUiState()
}


