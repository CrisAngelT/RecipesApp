package com.crisangel.recipesapp.data.mapper

import com.crisangel.recipesapp.data.model.ListRecipeResponse
import com.crisangel.recipesapp.data.model.RecipeResponse
import com.crisangel.recipesapp.domain.model.ListRecipeBean
import com.crisangel.recipesapp.domain.model.RecipeBean


fun RecipeResponse.toBean(): RecipeBean {
    return RecipeBean(
        recipes = this.recipes.map { it.toBean() }
    )
}


fun ListRecipeResponse.toBean(): ListRecipeBean {
    return ListRecipeBean(
        id = this.id,
        name = name,
        ingredients = ingredients,
        preparation = preparation,
        image = image,
        prepTime = prepTime,
        difficulty = difficulty,
        star = star
    )
}
