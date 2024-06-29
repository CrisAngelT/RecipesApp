package com.crisangel.recipesapp.presentation.recipescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.crisangel.recipesapp.R
import com.crisangel.recipesapp.commons.mapper.BeanMapper
import com.crisangel.recipesapp.commons.screens.AlertDialog
import com.crisangel.recipesapp.commons.screens.CircularProgress
import com.crisangel.recipesapp.commons.screens.StarRatingBar
import com.crisangel.recipesapp.domain.model.ListRecipeBean
import com.crisangel.recipesapp.domain.model.listRecipe
import com.crisangel.recipesapp.presentation.navigation.DetailAppScreen


@Composable
fun RecipesScreen(navController: NavHostController? = null) {
    RecipeScreenScaffold(navController)
}


@Composable
fun RecipeScreenScaffold(
    navController: NavHostController? = null
) {
    val viewModel: RecipesViewModel = hiltViewModel()
    val state = viewModel.recipesUiState.value
    val shouldShowDialog = rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue),
        topBar = {
            TopAppBar(viewModel)
        },
        containerColor = White)
    { paddingValues ->
        when (state) {
            RecipesUiState.Loading -> {
               CircularProgress()
            }

            is RecipesUiState.Success -> {
                if (state.listRecipeBean.isEmpty()) {
                    shouldShowDialog.value = true
                    AlertDialog(shouldShowDialog = shouldShowDialog, "No se encontraron resultados", onConfirmClick = {
                        shouldShowDialog.value = false
                        viewModel.clearQuery()
                    })
                } else {
                    shouldShowDialog.value = false
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(White)
                            .padding(paddingValues)
                    ) {
                        ContentRecipe(navController, state.listRecipeBean)
                    }
                }

            }

            is RecipesUiState.Error -> {
                AlertDialog(shouldShowDialog = shouldShowDialog, state.error, onConfirmClick = {
                    shouldShowDialog.value = false
                })
            }

        }

    }

}

@Preview
@Composable
fun ContentRecipe(navController: NavHostController? = null, listRecipeBean: List<ListRecipeBean> = listRecipe) {

    LazyColumn {
        items(listRecipeBean){ recipeBean ->
            ItemRecipe(recipeBean, navController)

        }
    }

}
@Preview
@Composable
private fun ItemRecipe(recipeBean: ListRecipeBean = ListRecipeBean(), navController: NavHostController? = null) {
    val context = LocalContext.current
    val rating by rememberSaveable { mutableStateOf(recipeBean.star?.toFloat())}
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
        ),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                val jsonRecipeBean = BeanMapper.toJson(recipeBean)
                navController?.navigate(DetailAppScreen(data = jsonRecipeBean))
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(recipeBean.image)
                    .error(R.drawable.ic_launcher_background)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.8f),
                loading = {
                    val strokeWidth = 5.dp
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = Color.LightGray,
                            strokeWidth = strokeWidth
                        )
                    }
                },
                contentScale = ContentScale.FillBounds,
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(160.dp)
                    .background(colorResource(id = R.color.color_primary))
                    .padding(top = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.svg_time), contentDescription = null,
                    tint = White
                )

                Text(
                    text = recipeBean.prepTime,
                    color = White,
                    modifier = Modifier
                        .padding(top = 5.dp),
                    style = TextStyle(
                        color = Black,
                        fontSize = 14.sp
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.svg_cook),
                    modifier = Modifier.padding(top = 10.dp), contentDescription = null,
                    tint = White
                )
                Text(
                    text = recipeBean.difficulty,
                    color = White,
                    modifier = Modifier
                        .padding(top = 5.dp),
                    style = TextStyle(
                        color = Black,
                        fontSize = 14.sp
                    )
                )
            }


        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                text = recipeBean.name,
                color = Black,
                modifier = Modifier
                    .padding(15.dp),
                style = TextStyle(
                    color = Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            StarRatingBar(
                maxStars = 5,
                rating = rating?:0f
            )

        }


    }
}

@Preview
@Composable
fun TopAppBar(viewModel: RecipesViewModel?=null) {
    Column(
        modifier = Modifier
            .background(White)
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 30.dp)
    ) {

        var getWordFilter by rememberSaveable { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.app_name_recipe),
                color = colorResource(id = R.color.color_primary),
                modifier = Modifier
                    .padding(top = 4.dp),
                style = TextStyle(
                    color = colorResource(id = R.color.color_primary),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(id = R.string.text_app),
                color = Black,
                modifier = Modifier
                    .padding(start = 4.dp, top = 8.dp),
                style = TextStyle(
                    color = Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold

                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.text_lookForYourRecipe),
            color = Black,
            modifier = Modifier
                .padding(top = 14.dp),
            style = TextStyle(
                color = Black,
                fontSize = 14.sp
            )
        )
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            TextField(
                modifier = Modifier
                    .weight(4f),
                value = getWordFilter,
                onValueChange = {
                    getWordFilter = it
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.text_search),
                        color = colorResource(id = R.color.color_semi_disabled)
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = colorResource(id = R.color.Color_TextFieldText),
                    focusedTextColor = Black,
                    focusedContainerColor = colorResource(id = R.color.color_container_default),
                    unfocusedContainerColor = colorResource(id = R.color.color_container_default),
                    disabledContainerColor = colorResource(id = R.color.color_container_default),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Black
                ),
                keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
                )
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val isWord = getWordFilter.isNotEmpty()
                FloatingActionButton(
                    modifier = Modifier
                        .height(55.dp)
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    onClick = {
                        viewModel?.setQuery(getWordFilter)
                    },
                    containerColor = if (isWord) {
                        colorResource(id = R.color.color_primary)
                    } else {
                        colorResource(id = R.color.color_semi_disabled)
                    },

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.svg_search),
                        contentDescription = null,
                        tint =
                        if (isWord) {
                            White
                        } else {
                            Black
                        },
                        modifier = Modifier.padding(20.dp)
                    )


                }


            }
        }
    }
}
