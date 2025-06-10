package com.sielehub.treasuremart.presentation.ui.product.wish

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sielehub.treasuremart.presentation.common.TopBar
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
fun WishListScreen(
    modifier: Modifier = Modifier,
    wishListViewModel: WishListViewModel = koinViewModel(),
    paddingValues: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit = {},
    onNavigateToProductDetail: (Int) -> Unit = {},
) {
    val wishListState = wishListViewModel.wishListState.collectAsStateWithLifecycle().value
    val removeFromWishListState =
        wishListViewModel.removeFromWishListState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        TopBar(
            navigationIcon = {
                FilledIconButton(
                    onClick = { onNavigateBack() },
                    modifier = modifier
                        .size(48.dp),
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    text = "Wish List",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier
                        .padding(end = 48.dp)
                )
            },
            actions = {}
        )
        when {
            wishListState.isLoading -> {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp
                    )
                }
            }

            wishListState.error != null -> {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = wishListState.error,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            wishListState.wishList.isEmpty() -> {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Wish List Item",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxHeight(1f),
                    contentPadding = PaddingValues(
                        top = 1.dp,
                    )
                ) {
                    items(items = wishListState.wishList) {
                        WishListProductCard(
                            product = it.product,
                            onClick = onNavigateToProductDetail,
                            onDelete = {
                                wishListViewModel.removeFromWishList(it)
                            }
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                    item {
                        Spacer(modifier = modifier.size(16.dp))
                    }
                }
            }
        }


    }
}