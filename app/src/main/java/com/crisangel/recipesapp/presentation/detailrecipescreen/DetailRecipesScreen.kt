package com.crisangel.recipesapp.presentation.detailrecipescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.crisangel.recipesapp.R
import com.crisangel.recipesapp.commons.mapper.BeanMapper
import com.crisangel.recipesapp.commons.screens.CircularProgress
import com.crisangel.recipesapp.domain.model.ListRecipeBean
import com.crisangel.recipesapp.presentation.navigation.DetailAppScreen

@Preview
@Composable
fun DetailRecipesScreen(jsonRecipe: DetailAppScreen = DetailAppScreen()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        val context = LocalContext.current
        val recipeBean = BeanMapper.fromJson(jsonRecipe.data, ListRecipeBean::class.java)
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(recipeBean.image)
                .error(R.drawable.ic_launcher_background)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            loading = {
                CircularProgress()
            },
            contentScale = ContentScale.FillBounds,
        )
        Column {
            Text(
                text = recipeBean.name,
                color = Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                style = TextStyle(
                    color = Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(id = R.string.text_ingredients),
                color = Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 20.dp),
                style = TextStyle(
                    color = Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            recipeBean.ingredients.forEach {
                Text(
                    text = it,
                    color = Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.color_semi_disabled),
                        fontSize = 16.sp
                    )
                )
            }

            Text(
                text = stringResource(id = R.string.text_preparation),
                color = Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                style = TextStyle(
                    color = Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = recipeBean.preparation ?: "",
                color = Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                style = TextStyle(
                    color = colorResource(id = R.color.color_semi_disabled),
                    fontSize = 16.sp
                )
            )


        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 50.dp)
        ) {
            Card(onClick = { }, colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.color_primary),
            ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                )) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(90.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.svg_time),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = White
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = recipeBean.prepTime,
                        fontSize = 14.sp,
                        color = White,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )

                }
            }
            Card(onClick = { }, colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.color_primary)
            ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                )) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.size(90.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.svg_cook),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = White
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = recipeBean.difficulty,
                        fontSize = 14.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                }

            }


        }

    }
}