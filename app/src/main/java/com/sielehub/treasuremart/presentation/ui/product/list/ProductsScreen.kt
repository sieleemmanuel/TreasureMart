package com.sielehub.treasuremart.presentation.ui.product.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.presentation.common.TopBar
import com.sielehub.treasuremart.core.composables.ProductCardGrid
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    productsViewModel: ProductsViewModel = koinViewModel(),
    productsQuery: String = "",
    onNavigateBack: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onSearchBarClick: (String) -> Unit = {},
    onNavigateToProductDetail: (Int) -> Unit = {},
) {
    val productsState by productsViewModel.productsState.collectAsStateWithLifecycle()
    val cartState by productsViewModel.cartState.collectAsStateWithLifecycle()
    var sortMenuExpanded by remember { mutableStateOf(false) }
    val sortMenuItems = remember {
        listOf(
            "Best match",
            "Latest",
            "Top sales",
            "Price low to high",
            "Price high to low"
        )
    }
    var selectedSortMenu by rememberSaveable {
        mutableStateOf(sortMenuItems[0])
    }
    val cartCount by remember {
        derivedStateOf {
            cartState.cart?.products?.filter { it.isSelected }?.size ?: 0
        }
    }

    LaunchedEffect(productsQuery) {
        productsViewModel.getProducts(productsQuery)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBar(
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                        contentDescription = null
                    )
                }
            },
            title = {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .clickable {
                            onSearchBarClick(productsQuery)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search),
                        modifier = modifier.padding(10.dp)
                    )
                    Text(
                        text = productsQuery,
                        modifier = modifier
                            .padding(end = 8.dp)
                    )
                }
            },
            actions = {
                FilledIconButton(
                    onClick = onNavigateToCart,
                    shape = RoundedCornerShape(1.dp),
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    if (cartCount > 0) {
                        BadgedBox(badge = {
                            Badge {
                                Text(text = cartCount.toString())
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingCart,
                                contentDescription = null
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        /*Spacer(modifier = modifier.height(16.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clip(RoundedCornerShape(24.dp))
                    .clickable {
                        onSearchBarClick(productsQuery)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.search),
                    modifier = modifier.padding(10.dp)
                )
                Text(
                    text = productsQuery,
                    modifier = modifier
                        .padding(end = 8.dp)
                )
            }
            FilledIconButton(
                onClick = onNavigateToCart,
                shape = RoundedCornerShape(1.dp),
                colors = IconButtonDefaults.iconButtonColors()
            ) {
                BadgedBox(badge = {
                    Badge {
                        Text(text = "9")
                    }
                }) {
                    Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null)
                }
            }
        }*/
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    .clickable {
                        sortMenuExpanded = !sortMenuExpanded
                    }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(selectedSortMenu, modifier = modifier.padding(vertical = 16.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
            }
            DropdownMenu(
                modifier = modifier.fillMaxWidth(),
                expanded = sortMenuExpanded,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(0),
                onDismissRequest = { sortMenuExpanded = false }
            ) {
                sortMenuItems.forEach {
                    DropdownMenuItem(
                        modifier = modifier.padding(horizontal = 8.dp),
                        text = { Text(it) },
                        onClick = {
                            selectedSortMenu = it
                            sortMenuExpanded = false
                        }
                    )
                }
            }

            Row(
                modifier = modifier
                    .clickable {
                        //TODO: Filter
                    }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            )
            {
                Text("Filter")
                Spacer(modifier = modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = null
                )
            }
        }

        when {
            productsState.isLoading -> {
                Box(
                    modifier = modifier.fillMaxSize(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 2.dp)
                }
            }

            productsState.error.isNotEmpty() -> {
                Column(
                    modifier = modifier.fillMaxSize(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = productsState.error)
                    Spacer(modifier = modifier.height(8.dp))
                    Button(onClick = {
                        productsViewModel.getProducts(productsQuery)
                    }) {
                        Text(text = "Retry")
                    }
                }
            }

            productsState.products.isEmpty() -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No products found")
                }
            }

            else -> {
                val products = productsState.products
                LazyVerticalGrid(
                    modifier = modifier,
                    columns = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = products) {
                        ProductCardGrid(
                            product = it,
                            onClick = { onNavigateToProductDetail(it.id) }
                        )
                    }
                    item {
                        Spacer(modifier = modifier.height(paddingValues.calculateBottomPadding()))
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview() {
    ProductsScreen()
}