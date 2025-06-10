package com.sielehub.treasuremart.presentation.ui.cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sielehub.treasuremart.presentation.base.MainViewModel
import com.sielehub.treasuremart.presentation.common.TopBar
import com.sielehub.treasuremart.presentation.ui.cart.component.CartCard
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    cartViewModel: CartViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToCheckout: () -> Unit = {},
) {
    val cartState by cartViewModel.cartState.collectAsStateWithLifecycle()
    val cartsState by cartViewModel.cartListState.collectAsStateWithLifecycle()
    var totalAmount by remember { mutableDoubleStateOf(0.0) }
    val cartAmount by cartViewModel.cartAmountState.collectAsStateWithLifecycle()
    val selProductsCount by remember {
        derivedStateOf {
            cartState.cart?.products?.filter { it.isSelected }?.size ?: 0
        }
    }

    LaunchedEffect(Unit) {
        Log.d("CartsScreen", "Carts: ${cartsState.carts}")
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            TopBar(
                navigationIcon = {},
                title = {
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        text = "Cart(${cartState.cart?.products?.size})"
                    )
                }
            )

            when {
                cartState.isLoading -> {
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

                cartState.error.isNotEmpty() -> {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cartState.error,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                cartState.cart?.products.isNullOrEmpty() -> {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = modifier.size(100.dp)
                        )
                        Spacer(modifier = modifier.height(10.dp))
                        Text(
                            text = "No products in cart yet. Add products to cart to see them here for checkout",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = modifier.padding(24.dp)
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = modifier.padding(horizontal = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(top = 10.dp)
                    ) {
                        items(items = cartState.cart!!.products) { cartProduct ->
                            CartCard(
                                cartProduct = cartProduct,
                                onReduceQuantity = {
                                    cartViewModel.updateCartProductQuantity(false, it)
                                },
                                onIncreaseQuantity = {
                                    cartViewModel.updateCartProductQuantity(true, it)
                                },
                                onSelected = {
                                    cartViewModel.checkCartProduct(it)
                                }
                            )
                        }
                        item { Spacer(modifier = modifier.height(82.dp)) }
                    }
                }
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "${
                    NumberFormat.getCurrencyInstance(Locale.getDefault()).format(cartAmount)
                }"
            )
            Button(
                enabled = selProductsCount > 0,
                onClick = { onNavigateToCheckout() },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Check Out($selProductsCount)")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartsScreenPreview() {
    CartsScreen()
}