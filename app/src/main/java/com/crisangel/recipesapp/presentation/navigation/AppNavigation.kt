package com.crisangel.recipesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.crisangel.recipesapp.presentation.detailrecipescreen.DetailRecipesScreen
import com.crisangel.recipesapp.presentation.recipescreen.RecipesScreen
import com.crisangel.recipesapp.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SplashAppScreen)
    {
        composable<SplashAppScreen> {
            SplashScreen(navController)

        }
        composable<RecipeAppScreen> {
            RecipesScreen(navController)

        }
        composable<DetailAppScreen> { backStackEntry ->
            val jsonRecipe = backStackEntry.toRoute<DetailAppScreen>()
            DetailRecipesScreen(jsonRecipe)
        }


    }
}