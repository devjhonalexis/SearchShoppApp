package jhon.solis.dev.searchshoppapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jhon.solis.dev.searchshoppapp.navigation.NavigationRoute
import jhon.solis.dev.searchshoppapp.ui.theme.SearchShoppAppTheme
import jhon.solis.dev.searchshoppapp.ui.theme.ShopAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchShoppAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ShopAppTheme.colors.backgroundColor
                ) {
                    val navController = rememberNavController()
                    NavigationRoute(navController = navController)
                }
            }
        }
    }
}