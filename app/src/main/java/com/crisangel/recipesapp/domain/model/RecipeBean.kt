package com.crisangel.recipesapp.domain.model

data class RecipeBean(
    val recipes: List<ListRecipeBean>

)


data class ListRecipeBean(
    val id: Int? = 0,
    val name: String = "",
    val ingredients: List<String> = listOf() ,
    val preparation: String? = "",
    val image: String = "",
    val prepTime: String  ="",
    val difficulty: String= "",
    val star:Int? = 2
)

val listRecipe = mutableListOf(
    ListRecipeBean(
        id = 1,
        name = "Ceviche",
        ingredients = listOf(
            "1 kilogramo de pescado a tu elección",
            "12 limones",
            "1 taza de caldo de pescado",
            "1 cebolla cortada en julianas",
            "1 pizca de pimienta",
            "1 ají amarillo picado",
            "Culantro",
            "Apio"
        ),
        preparation = "Cortar el pescado en cubos y ponerlos en un bol. Exprimir los limones y verter el jugo sobre el pescado. Agregar el caldo de pescado, la cebolla, la pimienta, el ají amarillo, el culantro y el apio. Mezclar bien y dejar reposar durante 10 minutos. Servir frío.",
        image = "",
        prepTime = "45 minutos",
        difficulty = "Media",
        star = 5

    ),

)

