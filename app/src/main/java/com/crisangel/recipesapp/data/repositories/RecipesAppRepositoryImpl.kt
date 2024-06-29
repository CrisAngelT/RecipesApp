package com.crisangel.recipesapp.data.repositories

import com.crisangel.recipesapp.data.mapper.toBean
import com.crisangel.recipesapp.data.remote.RecipesApi
import com.crisangel.recipesapp.domain.model.RecipeBean
import com.crisangel.recipesapp.domain.repositories.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipesAppRepositoryImpl @Inject constructor(private val recipesApi: RecipesApi) :
    RecipesRepository {
    override suspend fun recipesApp(): Flow<RecipeBean> {
        return flow {
            emit(recipesApi.recipesApi().toBean())
        }
    }

}