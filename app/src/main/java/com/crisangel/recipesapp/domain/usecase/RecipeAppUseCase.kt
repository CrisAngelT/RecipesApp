package com.crisangel.recipesapp.domain.usecase

import com.crisangel.recipesapp.commons.di.IoDispatcher
import com.crisangel.recipesapp.commons.resource.Resource
import com.crisangel.recipesapp.domain.repositories.RecipesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeAppUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val recipesRepository: RecipesRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading())
        emit(Resource.Success(recipesRepository.recipesApp()))
    }.catch {
        emit(Resource.DataError(it.message.toString()))
    }.flowOn(ioDispatcher)


}