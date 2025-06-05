package com.sielehub.treasuremart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.presentation.base.MainViewModel
import com.sielehub.treasuremart.presentation.common.BottomNavigationBar
import com.sielehub.treasuremart.presentation.ui.navigation.Navigation
import com.sielehub.treasuremart.presentation.ui.navigation.Route
import com.sielehub.treasuremart.presentation.ui.settings.SettingsViewModel
import com.sielehub.treasuremart.presentation.ui.theme.TreasureMartTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        lifecycleScope.launch {
            settingsViewModel.currentAppTheme.collect {
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
            TreasureMartTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = Route.toRoute(navBackStackEntry?.destination?.route)

                    val showBottomBar =
                        when (currentRoute) {
                            is Route.Dashboard,
                            is Route.Carts,
                            is Route.WishList,
                            is Route.Account -> true

                            else -> false
                        }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            val cartState by mainViewModel.cartState.collectAsState()
                            /*LaunchedEffect(cartState) {
                                Log.d(TAG, "Cart state: $cartState")
                                mainViewModel.getCart()
                            }*/
                            if (showBottomBar)
                                currentRoute?.let {
                                    BottomNavigationBar(
                                        navController = navController,
                                        currentRoute = currentRoute,
                                        cartCount = cartState.cart?.products?.size ?: 0
                                    )
                                }

                            /* NavigationBar(modifier = Modifier) {
                                 navItems.forEachIndexed { index, item ->
                                     NavigationBarItem(
                                         selected = index == selectedItem,
                                         onClick = {
                                             selectedItem = index
                                             navController.navigate(item.route) {
                                                 popUpTo(Route.Dashboard) {
                                                     saveState = false
                                                 }
                                                 launchSingleTop = true
                                                 restoreState = false
                                             }
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
                             }*/
                        }
                    ) { paddingValues ->
                        Navigation(
                            mainViewModel = mainViewModel,
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
