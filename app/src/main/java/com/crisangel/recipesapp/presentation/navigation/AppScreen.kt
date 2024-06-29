package com.crisangel.recipesapp.presentation.navigation
import com.crisangel.recipesapp.commons.json.jsonDetailRecipePreview
import kotlinx.serialization.Serializable

@Serializable
object SplashAppScreen
@Serializable
object RecipeAppScreen

@Serializable
data class DetailAppScreen(val data:String = jsonDetailRecipePreview)


