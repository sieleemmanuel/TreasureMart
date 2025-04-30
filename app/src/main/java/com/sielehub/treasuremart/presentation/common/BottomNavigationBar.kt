package com.sielehub.treasuremart.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.presentation.ui.navigation.Route

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: Route
) {
    val navItems = listOf(
        BottomNavItem(
            "Home",
            if (currentRoute == Route.Dashboard) Icons.Filled.Home else Icons.Outlined.Home,
            route = Route.Dashboard
        ),
        BottomNavItem(
            "Cart",
            if (currentRoute == Route.Carts) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
            Route.Carts
        ),
        BottomNavItem(
            "Wish",
            if (currentRoute == Route.WishList) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            Route.WishList
        ),
        BottomNavItem(
            "Account",
            if (currentRoute == Route.Account) Icons.Filled.Person else Icons.Outlined.Person,
            Route.Account
        )
    )
    NavigationBar(modifier = Modifier) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Route.Dashboard) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                icon = {
                    if (item.route is Route.Carts) {
                        BadgedBox(badge = {
                            Badge {
                                Text(text = Constants.myCart().products.size.toString())
                            }
                        }) {
                            Icon(
                                item.icon,
                                contentDescription = item.label
                            )
                        }
                    } else {
                        Icon(
                            item.icon,
                            contentDescription = item.label
                        )
                    }
                },
                label = { Text(item.label) }
            )
        }
    }
}