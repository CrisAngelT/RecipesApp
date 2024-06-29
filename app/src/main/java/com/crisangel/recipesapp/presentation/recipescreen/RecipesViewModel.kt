package com.crisangel.recipesapp.presentation.recipescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisangel.recipesapp.commons.resource.Resource
import com.crisangel.recipesapp.domain.usecase.RecipeAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class RecipesViewModel @Inject constructor(private val recipeAppUseCase: RecipeAppUseCase) :
    ViewModel() {
    private val _recipesUiState = mutableStateOf<RecipesUiState>(RecipesUiState.Loading)
    val recipesUiState: State<RecipesUiState> get() = _recipesUiState
    private val _query: MutableStateFlow<String> = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _query.debounce(100).collectLatest {
                getRecipes()
            }
        }
    }

    fun clearQuery(){
        _query.value = ""
    }
    fun setQuery(s: String) {
        _query.value = s
    }

      private fun getRecipes() {
        viewModelScope.launch {
            recipeAppUseCase.invoke().onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _recipesUiState.value = RecipesUiState.Loading
                    }

                    is Resource.Success -> {
                        resource.data?.collect { response ->
                            if (_query.value.isNotBlank()) {
                                _recipesUiState.value = RecipesUiState.Success(
                                    response.recipes.filter { filter ->
                                        filter.name.contains(_query.value, ignoreCase = true) ||
                                                filter.ingredients.any { ingredient -> ingredient.contains(_query.value, ignoreCase = true) }

                                    }
                                )
                            } else {
                                _recipesUiState.value = RecipesUiState.Success(response.recipes)
                            }

                        }


                    }

                    is Resource.DataError -> {
                        _recipesUiState.value =
                            RecipesUiState.Error(resource.errorMessageOrCode.toString())

                    }

                }
            }.launchIn(this)
        }
    }

}


