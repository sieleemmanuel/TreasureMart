package com.sielehub.treasuremart

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person2
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.presentation.common.BottomNavItem
import com.sielehub.treasuremart.presentation.ui.navigation.Navigation
import com.sielehub.treasuremart.presentation.ui.navigation.Route
import com.sielehub.treasuremart.presentation.ui.settings.SettingsViewModel
import com.sielehub.treasuremart.presentation.ui.theme.TreasureMartTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        lifecycleScope.launch {
            settingsViewModel.currentAppTheme.collect {
                Log.d(TAG, "onCreate theme: $it")
                val isDarkTheme = when (it) {
                    Constants.AppThemes.LIGHT -> false
                    Constants.AppThemes.DARK -> true
                    else -> false
                }
                enableEdgeToEdge(
                    statusBarStyle = if (isDarkTheme) SystemBarStyle.dark(
                        Color.Transparent.toArgb()
                    ) else SystemBarStyle.light(
                        Color.Transparent.toArgb(),
                        Color.Transparent.toArgb()
                    ),
                    navigationBarStyle = if (isDarkTheme) SystemBarStyle.dark(
                        Color.Transparent.toArgb()
                    ) else SystemBarStyle.light(
                        Color.Transparent.toArgb(),
                        Color.Transparent.toArgb()
                    )
                )
            }
        }
        lifecycleScope.launch {
            settingsViewModel.restartTriggered.collect {
                if (it) {
                    recreate()
                }
            }
        }

        setContent {
            val currentAppTheme by settingsViewModel.currentAppTheme.collectAsStateWithLifecycle()
            val isDarkTheme = when (currentAppTheme) {
                Constants.AppThemes.LIGHT -> false
                Constants.AppThemes.DARK -> true
                else -> isSystemInDarkTheme()
            }
            Log.d(TAG, "currentAppTheme dark: $isDarkTheme")
            TreasureMartTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navItems = listOf(
                        BottomNavItem("Home", Icons.Rounded.Home, route = Route.Dashboard),
                        BottomNavItem("Cart", Icons.Rounded.ShoppingCart, Route.Carts),
                        BottomNavItem("Wish", Icons.Rounded.Favorite, Route.WishList),
                        BottomNavItem("Account", Icons.Rounded.Person2, Route.Account)
                    )
                    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    val showBottomBar =
                        when (Route.toRoute(navBackStackEntry?.destination?.route)) {
                            is Route.Dashboard,
                            is Route.Carts,
                            is Route.WishList,
                            is Route.Account -> true

                            else -> false
                        }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (showBottomBar)
                                NavigationBar(modifier = Modifier) {
                                    navItems.forEachIndexed { index, item ->
                                        NavigationBarItem(
                                            selected = index == selectedItem,
                                            onClick = {
                                                selectedItem = index
                                                navController.navigate(item.route)
                                            },
                                            icon = {
                                                if (item.label == "Cart") {
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
                    ) { paddingValues ->
                        Navigation(
                            navController = navController,
                            paddingValues = paddingValues,
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
