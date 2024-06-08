package com.sielehub.treasuremart.presentation.cart

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sielehub.treasuremart.presentation.cart.component.CartCard
import com.sielehub.treasuremart.presentation.product.ProductsScreen
import com.sielehub.treasuremart.presentation.product.component.ProductCard

@Composable
fun CartsScreen(modifier: Modifier = Modifier) {
    val itemsCount = rememberSaveable {
        9
    }
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    text = "Cart($itemsCount)"
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            }

            LazyColumn(
                modifier = modifier.padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(itemsCount) {
                    CartCard()
                }
                item{ Spacer(modifier = modifier.height(82.dp))}
            }
        }
        Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = "KSh 156, 000"
        )
        ElevatedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.elevatedButtonColors(containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)) {
            Text(text = "Check Out($itemsCount)")
        }
    }
    }
}

@Preview(showBackground = true)
@Composable
fun CartsScreenPreview() {
    CartsScreen()
}