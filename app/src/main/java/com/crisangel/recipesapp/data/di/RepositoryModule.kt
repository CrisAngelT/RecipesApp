package com.crisangel.recipesapp.data.di

import com.crisangel.recipesapp.data.remote.RecipesApi
import com.crisangel.recipesapp.data.repositories.RecipesAppRepositoryImpl
import com.crisangel.recipesapp.domain.repositories.RecipesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideListRecipeRepository(
        recipesApi: RecipesApi,
    ): RecipesRepository = RecipesAppRepositoryImpl(recipesApi)
}