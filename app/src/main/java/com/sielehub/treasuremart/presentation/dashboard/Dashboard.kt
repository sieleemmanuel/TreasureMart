package com.sielehub.treasuremart.presentation.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.core.Constants.Companion.categories
import com.sielehub.treasuremart.core.Constants.Companion.products
import com.sielehub.treasuremart.presentation.common.BadgedIcon
import com.sielehub.treasuremart.presentation.common.CategoryCard
import com.sielehub.treasuremart.presentation.navigation.Route
import com.sielehub.treasuremart.presentation.product.component.ProductCardGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    navController: NavController,
    paddingValues: PaddingValues
) {
    var searchQuery by remember { mutableStateOf("") }
    LazyColumn(
        modifier = modifier
            .statusBarsPadding()
            .padding(bottom = paddingValues.calculateBottomPadding())
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Discover",
                    style = MaterialTheme.typography.titleLarge
                )
                FilledIconButton(
                    onClick = {
                        navController.navigate(Route.Notifications)
                    },
                    colors = IconButtonDefaults.iconButtonColors(),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    BadgedIcon(
                        icon = Icons.Outlined.Notifications,
                        count = Constants.myCart().products.size
                    )
                }
            }
            SearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                },
                onSearch = {},
                active = false,
                onActiveChange = {},
                shape = RoundedCornerShape(8.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                placeholder = {
                    Text(text = "Find products")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search"
                    )
                }
            ) {

            }
            Spacer(modifier = modifier.height(16.dp))
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = R.drawable.promotions,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.promotions),
                    modifier = modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = modifier.height(16.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 12.dp)
                )
                TextButton(
                    onClick = { navController.navigate(Route.Categories) },
                ) {
                    Text(text = "See all")
                    Spacer(modifier = modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                        contentDescription = null,
                        modifier = modifier.size(16.dp)
                    )
                }
            }
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .padding(horizontal = 12.dp)
            ) {
                items(items = categories()) { category ->
                    CategoryCard(category = category) {
                        navController.navigate(Route.Products(it))
                    }
                }
            }
            Spacer(modifier = modifier.height(16.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Best picks for you",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 12.dp)
                )
                TextButton(
                    onClick = { navController.navigate(Route.Products()) },
                ) {
                    Text(text = "See all")
                    Spacer(modifier = modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                        contentDescription = null,
                        modifier = modifier.size(16.dp)
                    )
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .padding(horizontal = 12.dp)
            ) {
                val products = products().filter { it.category == "men's clothing" }
                Log.d("Dashboard", "Products:$products ")
                items(products) { product ->
                    ProductCardGrid(
                        product = product,
                        addButton = false,
                        onFavClick = {},
                        onClick = {
                            navController.navigate(Route.ProductDetail(product.id))
                        })
                }
            }
            Spacer(modifier = modifier.navigationBarsPadding())
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Dashboard(modifier, navController, PaddingValues())
}