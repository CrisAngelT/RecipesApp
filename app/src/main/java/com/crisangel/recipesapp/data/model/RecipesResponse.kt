package com.crisangel.recipesapp.data.model

import com.squareup.moshi.Json

data class RecipeResponse(
    @field:Json(name = "recipes") val recipes: List<ListRecipeResponse>
)

data class ListRecipeResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "ingredients") val ingredients: List<String>,
    @field:Json(name = "preparation") val preparation: String?,
    @field:Json(name = "image") val image: String,
    @field:Json(name = "prepTime") val prepTime: String,
    @field:Json(name = "difficulty") val difficulty: String,
    @field:Json(name = "star") val star: Int?,
)