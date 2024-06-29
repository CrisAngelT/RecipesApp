package com.crisangel.recipesapp.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.crisangel.recipesapp.commons.screens.LottieSplashScreen
import com.crisangel.recipesapp.presentation.navigation.RecipeAppScreen
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(navHostController: NavHostController?=null) {
    LaunchedEffect(true) {
        delay(2000)
        navHostController?.popBackStack()
        navHostController?.navigate(RecipeAppScreen)

    }
    LottieSplashScreen()
}