package jhon.solis.dev.searchshoppapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jhon.solis.dev.searchshoppapp.feature.home.HomeScreen


@Composable
fun NavigationRoute(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = ShopAppRoute.Home
    ) {
        composable<ShopAppRoute.Home> {
            HomeScreen()
        }
    }
}