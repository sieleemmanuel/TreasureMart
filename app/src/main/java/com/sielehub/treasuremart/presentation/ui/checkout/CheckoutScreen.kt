package com.sielehub.treasuremart.presentation.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.core.Constants.Companion.products
import com.sielehub.treasuremart.presentation.ui.cart.component.SelectableRow
import com.sielehub.treasuremart.presentation.ui.navigation.Route
import com.sielehub.treasuremart.presentation.ui.product.component.ProductCardList

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavHostController,
) {
    val paymentMethods = listOf("MPESA", "Wallet")
    var selectedPaymentMethod by rememberSaveable { mutableStateOf(paymentMethods[0]) }
    var mpesaNumber by rememberSaveable { mutableStateOf(paymentMethods[0]) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(1f)
                    .statusBarsPadding()
                    .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                    .zIndex(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IconButton(
                    onClick = { navController.navigateUp() },
                    /*colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )*/
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Place Order", style = MaterialTheme.typography.titleLarge)
            }
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 10.dp),
            ) {
                item {
                    ElevatedCard(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Deliver Address",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp)
                            )
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    modifier = modifier.padding(start = 4.dp)
                                )
                            }

                        }
                        HorizontalDivider(modifier = modifier)
                        Spacer(modifier = modifier.height(8.dp))
                        Row {
                            Text(
                                text = "FirstName LastName",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = modifier.padding(start = 12.dp, end = 10.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "7123456789",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = modifier.height(8.dp))
                        Text(
                            text = LoremIpsum(20).values.first(),
                            modifier = modifier.padding(horizontal = 12.dp)
                        )
                        Spacer(modifier = modifier.height(10.dp))
                    }
                }
                item {
                    Spacer(modifier = modifier.height(16.dp))
                    ElevatedCard(
                        modifier = modifier
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "Payment Method",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = modifier.padding(start = 10.dp, top = 10.dp),
                        )

                        Text(
                            text = "100% Money Back Guarantee",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = modifier.padding(horizontal = 10.dp)
                        )
                        paymentMethods.forEach {
                            SelectableRow(
                                label = it,
                                selected = { selectedPaymentMethod == it },
                                numberProvider = { mpesaNo ->
                                    mpesaNumber = mpesaNo
                                },
                            ) {
                                selectedPaymentMethod = it
                            }
                        }
                        Spacer(modifier = modifier.height(10.dp))
                    }
                    Spacer(modifier = modifier.height(16.dp))
                }

                items(items = products().subList(0, 2)) {
                    ProductCardList(product = it) { productId ->
                        navController.navigate(Route.ProductDetail(productId))
                    }
                }
                item {
                    Spacer(modifier = modifier.height(6.dp))
                    ElevatedCard(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = modifier.padding(top = 10.dp, start = 10.dp),
                        )
                        Spacer(modifier = modifier.height(8.dp))
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                                text = "Product Amount",
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                                text = "KSh 18, 999",
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                                text = "Shipping fee",
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                                text = "KSh 258",
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                                text = "Payment Amount",
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                                text = "KSh 19, 257",
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                        Spacer(modifier = modifier.height(16.dp))
                    }
                    Spacer(modifier = modifier.height(100.dp))
                }
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "KSh 156, 000"
            )

            ElevatedButton(
                onClick = {

                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = modifier
                    .weight(1f)
                    .heightIn(48.dp)
            ) {
                Text(text = "Place Order")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview(modifier: Modifier = Modifier) {
    CheckoutScreen(
        paddingValues = PaddingValues(),
        navController = rememberNavController(),
    )
}

