package com.sielehub.treasuremart.presentation.ui.cart

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.core.Constants.Companion.myCart
import com.sielehub.treasuremart.presentation.common.TopBar
import com.sielehub.treasuremart.presentation.ui.cart.component.CartCard
import com.sielehub.treasuremart.presentation.ui.navigation.Route

@Composable
fun CartsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val cart = myCart()
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
                        text = "Cart(${cart.products.size})"
                    )
                }
            )
            /* Row(
                 modifier = modifier
                     .fillMaxWidth()
                     .background(color = MaterialTheme.colorScheme.surfaceContainer)
                     .statusBarsPadding()
                     .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.SpaceBetween
             ) {
                 Text(
                     fontWeight = FontWeight.Bold,
                     fontSize = 20.sp,
                     text = "Cart(${cart.products.size})"
                 )
                 IconButton(onClick = { *//*TODO*//* }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            }*/

            LazyColumn(
                modifier = modifier.padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(top = 10.dp)
            ) {
                items(items = cart.products) {
                    CartCard(cartProduct = it)
                }
                item { Spacer(modifier = modifier.height(82.dp)) }
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
            val total = cart.products.map {}
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "KSh 156, 000"
            )
            Button(
                onClick = {
                    navController.navigate(Route.Checkout)
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Check Out(${cart.products.size})")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartsScreenPreview() {
    CartsScreen(paddingValues = PaddingValues(0.dp), navController = rememberNavController())
}