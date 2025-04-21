package com.sielehub.treasuremart.presentation.ui.product.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.presentation.ui.navigation.Route
import com.sielehub.treasuremart.presentation.ui.product.component.ProductCardGrid
import com.sielehub.treasuremart.presentation.ui.product.list.ProductListState
import java.util.Locale

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavController,
    categoryListState: CategoryListState = CategoryListState(),
    productsListState: ProductListState = ProductListState()
) {
    val categories = categoryListState.categories

    Column(modifier = modifier.statusBarsPadding()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Categories",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
        }

        LazyColumn(
            modifier = modifier.padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(items = categories) { categoryItem ->
                val categoryProducts by remember {
                    derivedStateOf {
                        productsListState.products.filter { it.category == categoryItem }
                    }
                }
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = categoryItem.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Row(
                        modifier = modifier
                            .clickable {
                                navController.navigate(route = Route.Products(categoryItem))
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "All",
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }

                }

                LazyRow(
                    modifier = modifier.padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = categoryProducts.takeIf { it.size >= 4 }?.take(4)
                            ?: categoryProducts,
                        key = { it.id }
                    ) { product ->
                        ProductCardGrid(
                            product = product
                        ) {
                            navController.navigate(route = "${Route.ProductDetail}/${product.id}")
                        }
                    }
                }
            }
            item { Spacer(modifier = modifier.height(82.dp)) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesScreenPreview(modifier: Modifier = Modifier) {
    CategoriesScreen(
        modifier, PaddingValues(),
        rememberNavController(),
        CategoryListState(),
        ProductListState()
    )
}