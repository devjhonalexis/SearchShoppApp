package jhon.solis.dev.domain.dto

data class ProductDto(
    val productId: String?,
    val productDisplayName: String?,
    val listPrice: Double?,
    val promoPrice: Double?,
    val smImage: String?,
    val lgImage: String?,
    val xlImage: String?,
    val variantsColor: List<VariantColorDto>?
)

data class VariantColorDto(
    val colorName: String?,
    val colorHex: String?
)