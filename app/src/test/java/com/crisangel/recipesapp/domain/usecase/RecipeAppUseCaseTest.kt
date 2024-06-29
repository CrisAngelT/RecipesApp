package com.crisangel.recipesapp.domain.usecase

import com.crisangel.recipesapp.commons.coroutine.TestCoroutineDispatcher
import com.crisangel.recipesapp.commons.resource.Resource
import com.crisangel.recipesapp.domain.model.ListRecipeBean
import com.crisangel.recipesapp.domain.model.RecipeBean
import com.crisangel.recipesapp.domain.repositories.RecipesRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RecipeAppUseCaseTest {
    @RelaxedMockK
    private lateinit var recipesRepository: RecipesRepository

    private lateinit var recipeAppUseCase: RecipeAppUseCase
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun onBefore() {
        recipesRepository = mockk()
        recipeAppUseCase = RecipeAppUseCase(testDispatcher, recipesRepository)
    }


    @Test
    fun `when You Receive Recipes from api`() = runBlocking {
        //Given
        val listMockRecipes = listOf(
            ListRecipeBean(
                id = 1,
                name = "Ceviche",
                ingredients = listOf("Fish", "Lime"),
                preparation = "Mix fish and lime"
            )
        )
        val mockRecipes = RecipeBean(
            recipes = listMockRecipes
        )
        coEvery {
            recipesRepository.recipesApp()
        } returns flowOf(mockRecipes)
        //When
        recipeAppUseCase.invoke().collect { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    resource.data?.collect { response ->
                        assert(response == mockRecipes)
                    }
                }

                is Resource.DataError -> {}

            }
        }

    }
    @Test
    fun `when API does not return recipes`() = runBlocking {
        val recipeBean = RecipeBean(listOf())

        coEvery { recipesRepository.recipesApp() } returns flowOf(recipeBean)

        recipeAppUseCase.invoke().collect { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    resource.data?.collect { response ->
                        assert(response == recipeBean)
                    }
                }

                is Resource.DataError -> {}

            }
        }
    }

}