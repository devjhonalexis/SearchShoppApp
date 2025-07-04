package jhon.solis.dev.domain.model

data class Product(
    val id: String,
    val productName: String,
    val priceBeforeDiscount: Double,
    val priceAfterDiscount: Double,
    val imageUrl: String,
    val colors: List<String>
)
