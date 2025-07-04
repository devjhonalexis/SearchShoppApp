package jhon.solis.dev.searchshoppapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface ShopAppRoute {
    @Serializable
    data object Home : ShopAppRoute
}